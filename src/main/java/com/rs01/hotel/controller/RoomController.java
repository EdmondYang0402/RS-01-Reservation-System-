package com.rs01.hotel.controller;

import com.rs01.common.result.Result;
import com.rs01.hotel.entity.Room;
import com.rs01.hotel.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/{id}")
    public Result<Room> get(@PathVariable Long id) { return Result.success(roomService.getById(id)); }

    @GetMapping
    public Result<List<Room>> list(@RequestParam Long hotelId,
                                   @RequestParam(defaultValue = "0") long offset,
                                   @RequestParam(defaultValue = "20") int limit) {
        return Result.success(roomService.listByHotelId(hotelId, offset, limit));
    }

    @PostMapping
    public Result<Room> create(@Valid @RequestBody Room room) {
        roomService.create(room);
        return Result.success(room);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody Room room) {
        room.setId(id);
        roomService.update(room);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return Result.success();
    }
}
