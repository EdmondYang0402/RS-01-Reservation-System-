package com.rs01.reservation.controller;

import com.rs01.common.result.Result;
import com.rs01.reservation.dto.ReservationCreateDTO;
import com.rs01.reservation.service.ReservationService;
import com.rs01.reservation.vo.ReservationDetailVO;
import com.rs01.reservation.vo.ReservationListVO;
import com.rs01.security.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations")
@Validated
public class ReservationController {
    private final ReservationService reservationService;
    private final CurrentUserService currentUserService;

    @GetMapping
    @Operation(summary = "List current user's reservations")
    public Result<List<ReservationListVO>> list() {
        Long userId = currentUserService.getCurrentUserId();
        return Result.success(reservationService.getMyReservations(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get current user's reservation")
    public Result<ReservationDetailVO> detail(@PathVariable @Positive Long id) {
        Long userId = currentUserService.getCurrentUserId();
        return Result.success(reservationService.getReservationDetail(id, userId));
    }

    @PostMapping
    @Operation(summary = "Create reservation")
    public Result<ReservationDetailVO> create(@Valid @RequestBody ReservationCreateDTO request) {
        return Result.success(reservationService.createReservation(request));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel reservation")
    public Result<Void> cancel(@PathVariable @Positive Long id) {
        reservationService.cancelReservation(id);
        return Result.success();
    }
}
