package com.rs01.inventory.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AvailableRoomTypeVO {
    private Long roomTypeId;
    private String roomTypeName;
    private Integer capacity;
    private String bedType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long nightCount;
    private Integer availableRooms;
    private BigDecimal totalAmount;
}
