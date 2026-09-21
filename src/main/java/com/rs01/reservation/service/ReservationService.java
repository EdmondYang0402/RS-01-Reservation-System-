package com.rs01.reservation.service;

import com.rs01.common.exception.BusinessException;
import com.rs01.hotel.entity.Room;
import com.rs01.hotel.entity.RoomStatus;
import com.rs01.hotel.entity.RoomType;
import com.rs01.hotel.mapper.RoomMapper;
import com.rs01.hotel.mapper.RoomTypeMapper;
import com.rs01.inventory.mapper.DailyInventoryMapper;
import com.rs01.reservation.dto.ReservationCreateDTO;
import com.rs01.reservation.entity.Reservation;
import com.rs01.reservation.entity.ReservationNight;
import com.rs01.reservation.entity.ReservationStatus;
import com.rs01.reservation.mapper.ReservationMapper;
import com.rs01.reservation.mapper.ReservationNightMapper;
import com.rs01.reservation.vo.ReservationDetailVO;
import com.rs01.reservation.vo.ReservationListVO;
import com.rs01.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final ReservationMapper reservationMapper;
    private final ReservationNightMapper reservationNightMapper;
    private final DailyInventoryMapper dailyInventoryMapper;
    private final RoomTypeMapper roomTypeMapper;
    private final RoomMapper roomMapper;
    private final CurrentUserService currentUserService;

    public List<ReservationListVO> getMyReservations(Long userId) {
        log.debug("Listing reservations for userId={}", userId);
        return reservationMapper.selectByUserId(userId);
    }

    public ReservationDetailVO getReservationDetail(Long reservationId, Long userId) {
        log.debug("Loading reservation detail: reservationId={}, userId={}", reservationId, userId);
        Reservation reservation = reservationMapper.selectById(reservationId);
        if (reservation == null || !userId.equals(reservation.getUserId())) {
            throw new BusinessException("Reservation not found");
        }
        ReservationDetailVO detail = reservationMapper.selectDetailById(reservationId);
        detail.setNights(reservationNightMapper.selectByReservationId(reservationId));
        return detail;
    }

    public List<ReservationListVO> getArrivals(LocalDate date) {
        return reservationMapper.selectArrivals(date);
    }

    public List<ReservationListVO> getDepartures(LocalDate date) {
        return reservationMapper.selectDepartures(date);
    }


    @Transactional
    public ReservationDetailVO createReservation(ReservationCreateDTO dto) {

        Long userId = currentUserService.getCurrentUserId();

        // 1. 基础参数校验
        LocalDate checkInDate = dto.getCheckInDate();
        LocalDate checkOutDate = dto.getCheckOutDate();

        if (checkInDate == null || checkOutDate == null) {
            throw new BusinessException("入住日期和退房日期不能为空");
        }

        if (checkInDate.isBefore(LocalDate.now())) {
            throw new BusinessException("入住日期不能早于今天");
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new BusinessException("退房日期必须晚于入住日期");
        }

        // 2. 查可预订房型
        RoomType roomType = roomTypeMapper.selectBookableRoomType(
                dto.getRoomTypeId(),
                dto.getGuestCount()
        );

        if (roomType == null) {
            throw new BusinessException("房型不存在、已停用或无法容纳当前入住人数");
        }

        // 3. 计算晚数和总价
        long nightCount = ChronoUnit.DAYS.between(
                checkInDate,
                checkOutDate
        );

        BigDecimal totalAmount = roomType.getBasePrice()
                .multiply(BigDecimal.valueOf(nightCount));

        // 4. 逐晚扣库存
        LocalDate stayDate = checkInDate;

        while (stayDate.isBefore(checkOutDate)) {

            int affectedRows = dailyInventoryMapper.reserveOne(
                    roomType.getId(),
                    stayDate
            );

            if (affectedRows != 1) {
                throw new BusinessException(
                        stayDate + " 房量不足"
                );
            }

            stayDate = stayDate.plusDays(1);
        }

        // 5. 创建预约主单
        Reservation reservation = new Reservation();

        reservation.setReservationNo(generateReservationNo());
        reservation.setUserId(userId);
        reservation.setHotelId(roomType.getHotelId());
        reservation.setRoomTypeId(roomType.getId());

        // 预订阶段还没有分配具体房间
        reservation.setAssignedRoomId(null);

        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);

        reservation.setGuestName(dto.getGuestName());
        reservation.setGuestPhone(dto.getGuestPhone());
        reservation.setGuestCount(dto.getGuestCount());

        reservation.setTotalAmount(totalAmount);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        reservationMapper.insert(reservation);

        // insert 必须回填 reservation.id
        Long reservationId = reservation.getId();

        // 6. 创建每晚快照
        stayDate = checkInDate;

        while (stayDate.isBefore(checkOutDate)) {

            ReservationNight reservationNight = new ReservationNight();

            reservationNight.setReservationId(reservationId);
            reservationNight.setStayDate(stayDate);
            reservationNight.setPrice(roomType.getBasePrice());

            reservationNightMapper.insert(reservationNight);

            stayDate = stayDate.plusDays(1);
        }

        // 7. 组装返回 VO
        ReservationDetailVO vo = new ReservationDetailVO();

        vo.setReservationId(reservation.getId());
        vo.setReservationNo(reservation.getReservationNo());

        vo.setRoomTypeName(roomType.getName());

        vo.setCheckInDate(checkInDate);
        vo.setCheckOutDate(checkOutDate);

        vo.setGuestName(dto.getGuestName());
        vo.setGuestPhone(dto.getGuestPhone());
        vo.setGuestCount(dto.getGuestCount());

        vo.setTotalAmount(totalAmount);
        vo.setStatus(ReservationStatus.CONFIRMED);

        return vo;
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Long userId = currentUserService.getCurrentUserId();

        Reservation reservation = reservationMapper.
                selectByIdAndUserId(reservationId,userId);
        if (reservation == null) {
            throw new BusinessException("预约不存在");
        }

        int affectedRows = reservationMapper.updateStatus(
                reservationId,
                ReservationStatus.CONFIRMED,
                ReservationStatus.CANCELLED
        );

        if (affectedRows != 1) {
            throw new BusinessException("当前预约状态不允许取消");
        }

        List<ReservationNight> nights =
                reservationNightMapper.selectByReservationId(reservationId);

        for (ReservationNight night : nights) {

            int rows = dailyInventoryMapper.releaseOne(
                    reservation.getRoomTypeId(),
                    night.getStayDate()
            );

            if (rows != 1) {
                throw new BusinessException("库存恢复失败");
            }
        }

    }

    @Transactional
    public void checkIn(Long reservationId, Long roomId) {

        // 1. 查预约
        Reservation reservation = reservationMapper.selectById(reservationId);

        if (reservation == null) {
            throw new BusinessException("预约不存在");
        }

        // 2. 状态必须是 CONFIRMED
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new BusinessException("当前预约状态不允许办理入住");
        }

        // 3. V1：只允许入住日当天办理入住
        if (!reservation.getCheckInDate().equals(LocalDate.now())) {
            throw new BusinessException("当前日期不允许办理入住");
        }

        // 4. 查具体房间
        Room room = roomMapper.selectById(roomId);

        if (room == null) {
            throw new BusinessException("房间不存在");
        }

        // 5. 房间类型必须和预约房型一致
        if (!room.getRoomTypeId().equals(reservation.getRoomTypeId())) {
            throw new BusinessException("房间类型与预约房型不匹配");
        }

        // 6. 房间必须 AVAILABLE
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new BusinessException("当前房间不可入住");
        }

        // 7. CAS 抢占具体房间
        int roomRows = roomMapper.occupyRoom(roomId);

        if (roomRows != 1) {
            throw new BusinessException("房间已被其他入住操作占用");
        }

        // 8. CAS 更新预约状态
        int reservationRows = reservationMapper.checkInReservation(
                reservationId,
                roomId
        );

        if (reservationRows != 1) {
            throw new BusinessException("预约状态已发生变化，入住失败");
        }
    }

    public void checkOut(Long reservationId) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    public void markNoShow(Long reservationId) {
        // TODO: core business logic should be implemented manually by developer
        throw unsupported();
    }

    private String generateReservationNo() {
        return "R"
                + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();
    }

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Core reservation logic has not been implemented");
    }
}
