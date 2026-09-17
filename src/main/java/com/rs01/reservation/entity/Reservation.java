package com.rs01.reservation.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Reservation {
    private Long id;
    private String reservationNo;
    private Long userId;
    private Long hotelId;
    private Long roomTypeId;
    private Long assignedRoomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestName;
    private String guestPhone;
    private Integer guestCount;
    private BigDecimal totalAmount;
    private ReservationStatus status;
    private LocalDateTime actualCheckInTime;
    private LocalDateTime actualCheckOutTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
