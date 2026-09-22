package com.rs01.hotel.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelListVO {

    private Long hotelId;

    private String hotelName;

    private String address;

    private String coverImageUrl;

    private BigDecimal minBasePrice;
}
