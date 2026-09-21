package com.rs01.reservation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CheckInDTO {
    @NotNull
    @Positive
    private Long roomId;
}
