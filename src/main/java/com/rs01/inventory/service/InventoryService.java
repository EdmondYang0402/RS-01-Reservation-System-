package com.rs01.inventory.service;

import com.rs01.common.exception.BusinessException;
import com.rs01.hotel.entity.RoomType;
import com.rs01.inventory.dto.AvailabilitySearchDTO;
import com.rs01.inventory.mapper.DailyInventoryMapper;
import com.rs01.inventory.vo.AvailableRoomTypeVO;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;


@Service
public class InventoryService {
    DailyInventoryMapper dailyInventoryMapper;

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
