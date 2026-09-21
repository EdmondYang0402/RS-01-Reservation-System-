package com.rs01.reservation.controller;

import com.rs01.common.result.Result;
import com.rs01.hotel.entity.Room;
import com.rs01.hotel.service.RoomService;
import com.rs01.reservation.dto.CheckInDTO;
import com.rs01.reservation.service.ReservationService;
import com.rs01.reservation.vo.ReservationListVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/front-desk")
@RequiredArgsConstructor
@Tag(name = "Front Desk")
@Validated
public class FrontDeskController {
    private final ReservationService reservationService;
    private final RoomService roomService;

    @GetMapping("/arrivals")
    @Operation(summary = "List today's arrivals")
    public Result<List<ReservationListVO>> arrivals() {
        return Result.success(reservationService.getArrivals(LocalDate.now()));
    }

    @GetMapping("/departures")
    @Operation(summary = "List today's departures")
    public Result<List<ReservationListVO>> departures() {
        return Result.success(reservationService.getDepartures(LocalDate.now()));
    }

    @GetMapping("/rooms")
    @Operation(summary = "List rooms for front desk")
    public Result<List<Room>> rooms(@RequestParam @Positive Long hotelId,
                                    @RequestParam(defaultValue = "0") long offset,
                                    @RequestParam(defaultValue = "100") int limit) {
        return Result.success(roomService.listByHotelId(hotelId, offset, limit));
    }

    @GetMapping("/rooms/available")
    @Operation(summary = "List available rooms by room type")
    public Result<List<Room>> availableRooms(@RequestParam @Positive Long roomTypeId) {
        return Result.success(roomService.listAvailableByRoomTypeId(roomTypeId));
    }

    @PostMapping("/reservations/{id}/check-in")
    @Operation(summary = "Check in reservation")
    public Result<Void> checkIn(@PathVariable @Positive Long id, @Valid @RequestBody CheckInDTO request) {
        reservationService.checkIn(id, request.getRoomId());
        return Result.success();
    }

    @PostMapping("/reservations/{id}/check-out")
    @Operation(summary = "Check out reservation")
    public Result<Void> checkOut(@PathVariable @Positive Long id) {
        reservationService.checkOut(id);
        return Result.success();
    }

    @PostMapping("/reservations/{id}/no-show")
    @Operation(summary = "Mark reservation as no-show")
    public Result<Void> noShow(@PathVariable @Positive Long id) {
        reservationService.markNoShow(id);
        return Result.success();
    }
}
