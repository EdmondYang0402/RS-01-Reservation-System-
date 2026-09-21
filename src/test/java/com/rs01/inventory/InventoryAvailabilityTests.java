package com.rs01.inventory;

import com.rs01.common.exception.BusinessException;
import com.rs01.hotel.mapper.RoomTypeMapper;
import com.rs01.inventory.dto.AvailabilitySearchDTO;
import com.rs01.inventory.mapper.DailyInventoryMapper;
import com.rs01.inventory.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class InventoryAvailabilityTests {
    @Mock private DailyInventoryMapper dailyInventoryMapper;
    @Mock private RoomTypeMapper roomTypeMapper;

    @Test
    void rejectsPastCheckInDate() {
        InventoryService service = new InventoryService(dailyInventoryMapper, roomTypeMapper);
        AvailabilitySearchDTO request = new AvailabilitySearchDTO();
        request.setCheckInDate(LocalDate.now().minusDays(1));
        request.setCheckOutDate(LocalDate.now().plusDays(1));
        request.setGuestCount(1);

        assertThatThrownBy(() -> service.searchAvailability(request))
                .isInstanceOf(BusinessException.class);
    }
}
