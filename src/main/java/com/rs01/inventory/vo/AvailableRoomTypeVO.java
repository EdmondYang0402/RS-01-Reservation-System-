package com.rs01.inventory.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AvailableRoomTypeVO {
    private Long roomTypeId;
    private String name;
    private Integer capacity;
    private String bedType;
    private BigDecimal basePrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long nightCount;
    private Integer availableRooms;
    private BigDecimal totalAmount;
}
