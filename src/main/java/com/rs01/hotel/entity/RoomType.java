package com.rs01.hotel.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RoomType {
    private Long id;
    private Long hotelId;
    private String name;
    private String description;
    private Integer capacity;
    private String bedType;
    private BigDecimal basePrice;
    private Integer totalRooms;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
