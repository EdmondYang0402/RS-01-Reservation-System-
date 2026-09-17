package com.rs01.inventory.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AvailabilitySearchDTO {
    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;
    @NotNull
    @Future
    private LocalDate checkOutDate;
    @NotNull
    @Min(1)
    private Integer guestCount;
}
