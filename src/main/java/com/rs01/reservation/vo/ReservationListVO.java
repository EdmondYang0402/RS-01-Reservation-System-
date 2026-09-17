package com.rs01.reservation.vo;

import com.rs01.reservation.entity.ReservationStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationListVO {
    private Long id;
    private String reservationNo;
    private String hotelName;
    private String roomTypeName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalAmount;
    private ReservationStatus status;
    private LocalDateTime createTime;
}
