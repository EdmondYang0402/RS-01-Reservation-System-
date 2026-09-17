package com.rs01.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationCreateDTO {
    @NotNull
    private Long roomTypeId;
    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;
    @NotNull
    @Future
    private LocalDate checkOutDate;
    @NotBlank
    @Size(max = 100)
    private String guestName;
    @NotBlank
    @Size(max = 32)
    private String guestPhone;
    @NotNull
    @Min(1)
    private Integer guestCount;
}
