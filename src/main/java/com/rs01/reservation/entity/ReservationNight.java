package com.rs01.reservation.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationNight {
    private Long id;
    private Long reservationId;
    private LocalDate stayDate;
    private BigDecimal price;
    private LocalDateTime createTime;
}
