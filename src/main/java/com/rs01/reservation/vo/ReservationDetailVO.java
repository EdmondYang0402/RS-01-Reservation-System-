package com.rs01.reservation.vo;

import com.rs01.reservation.entity.ReservationNight;
import com.rs01.reservation.entity.ReservationStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationDetailVO {
    private Long reservationId;
    private String reservationNo;
    private String hotelName;
    private String roomTypeName;
    private String assignedRoomNumber;
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
    private List<ReservationNight> nights;
}
