package com.rs01.hotel.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AvailableHotelVO {

    private Long hotelId;

    private String hotelName;

    private String address;

    // 当前搜索条件下有多少种可订房型
    private Integer availableRoomTypeCount;

    // 当前搜索条件下最便宜的总住宿价格
    private BigDecimal minTotalAmount;
}