package com.rs01.hotel.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Hotel {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
