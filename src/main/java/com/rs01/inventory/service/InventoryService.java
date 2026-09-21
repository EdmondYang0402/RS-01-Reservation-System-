package com.rs01.inventory.service;

import com.rs01.common.exception.BusinessException;
import com.rs01.hotel.entity.RoomType;
import com.rs01.hotel.mapper.RoomTypeMapper;
import com.rs01.inventory.dto.AvailabilitySearchDTO;
import com.rs01.inventory.dto.InventoryInitializeDTO;
import com.rs01.inventory.entity.DailyInventory;
import com.rs01.inventory.mapper.DailyInventoryMapper;
import com.rs01.inventory.vo.AvailableRoomTypeVO;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final DailyInventoryMapper dailyInventoryMapper;
    private final RoomTypeMapper roomTypeMapper;

    public List<DailyInventory> getInventory(Long roomTypeId, LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        return dailyInventoryMapper.selectByRoomTypeIdAndDateRange(roomTypeId, startDate, endDate);
    }

    public int initializeInventory(InventoryInitializeDTO request) {
        validateDateRange(request.getStartDate(), request.getEndDate());
        RoomType roomType = roomTypeMapper.selectById(request.getRoomTypeId());
        if (roomType == null) {
            throw new BusinessException("Room type not found");
        }

        int initialized = 0;
        for (LocalDate date = request.getStartDate(); date.isBefore(request.getEndDate()); date = date.plusDays(1)) {
            if (dailyInventoryMapper.selectByRoomTypeIdAndStayDate(roomType.getId(), date) != null) {
                continue;
            }
            DailyInventory inventory = new DailyInventory();
            inventory.setRoomTypeId(roomType.getId());
            inventory.setStayDate(date);
            inventory.setTotalInventory(roomType.getTotalRooms());
            inventory.setReservedCount(0);
            inventory.setOutOfServiceCount(0);
            initialized += dailyInventoryMapper.insert(inventory);
        }
        log.info("Inventory initialized: roomTypeId={}, startDate={}, endDate={}, inserted={}",
                request.getRoomTypeId(), request.getStartDate(), request.getEndDate(), initialized);
        return initialized;
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new BusinessException("End date must be after start date");
        }
    }

    public List<AvailableRoomTypeVO> searchAvailability(AvailabilitySearchDTO dto) {

        LocalDate checkInDate = dto.getCheckInDate();
        LocalDate checkOutDate = dto.getCheckOutDate();
        Integer guestCount = dto.getGuestCount();

        if (checkInDate == null || checkOutDate == null) {
            throw new BusinessException("入住日期和退房日期不能为空");
        }

        if (guestCount == null || guestCount <= 0) {
            throw new BusinessException("入住人数必须大于0");
        }

        if (checkInDate.isBefore(LocalDate.now())) {
            throw new BusinessException("入住日期不能早于今天");
        }

        if (!checkOutDate.isAfter(checkInDate)) {
            throw new BusinessException("退房日期必须晚于入住日期");
        }

        return dailyInventoryMapper.selectAvailableRoomTypes(
                checkInDate,
                checkOutDate,
                guestCount
        );
    }

}
