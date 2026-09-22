package com.rs01.hotel.controller;

import com.rs01.common.result.Result;
import com.rs01.hotel.entity.Room;
import com.rs01.hotel.service.RoomService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
@Tag(name = "Admin Rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{id}")
    @Operation(summary = "Get room")
    public Result<Room> get(@PathVariable Long id) { return Result.success(roomService.getById(id)); }

    @GetMapping
    @Operation(summary = "List rooms")
    public Result<List<Room>> list(@RequestParam Long hotelId,
                                   @RequestParam(defaultValue = "0") long offset,
                                   @RequestParam(defaultValue = "20") int limit) {
        return Result.success(roomService.listByHotelId(hotelId, offset, limit));
    }

    @PostMapping
    @Operation(summary = "Create room")
    public Result<Room> create(@Valid @RequestBody Room room) {
        roomService.create(room);
        return Result.success(room);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody Room room) {
        room.setId(id);
        roomService.update(room);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room")
    public Result<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return Result.success();
    }
}
