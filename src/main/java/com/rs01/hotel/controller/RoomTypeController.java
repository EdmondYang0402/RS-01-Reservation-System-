package com.rs01.hotel.controller;

import com.rs01.common.result.Result;
import com.rs01.hotel.entity.RoomType;
import com.rs01.hotel.service.RoomTypeService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/room-types")
@RequiredArgsConstructor
@Tag(name = "Admin Room Types")
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @GetMapping("/{id}")
    @Operation(summary = "Get room type")
    public Result<RoomType> get(@PathVariable Long id) { return Result.success(roomTypeService.getById(id)); }

    @GetMapping
    @Operation(summary = "List room types")
    public Result<List<RoomType>> list(@RequestParam Long hotelId,
                                       @RequestParam(defaultValue = "0") long offset,
                                       @RequestParam(defaultValue = "20") int limit) {
        return Result.success(roomTypeService.listByHotelId(hotelId, offset, limit));
    }

    @PostMapping
    @Operation(summary = "Create room type")
    public Result<RoomType> create(@Valid @RequestBody RoomType roomType) {
        roomTypeService.create(roomType);
        return Result.success(roomType);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room type")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody RoomType roomType) {
        roomType.setId(id);
        roomTypeService.update(roomType);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room type")
    public Result<Void> delete(@PathVariable Long id) {
        roomTypeService.delete(id);
        return Result.success();
    }
}
