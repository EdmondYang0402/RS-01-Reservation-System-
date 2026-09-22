package com.rs01.hotel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelAvailabilitySearchDTO {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer guestCount;

    // V2 当前可以先用酒店名/地址模糊搜索
    private String keyword;
}