package com.rs01.hotel.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Room {
    private Long id;
    private Long hotelId;
    private Long roomTypeId;
    private String roomNumber;
    private Integer floor;
    private RoomStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
