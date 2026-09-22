package com.rs01.hotel.controller;

import com.rs01.common.result.Result;
import com.rs01.hotel.dto.HotelAvailabilitySearchDTO;
import com.rs01.hotel.entity.Hotel;
import com.rs01.hotel.service.HotelService;
import com.rs01.hotel.vo.AvailableHotelVO;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Tag(name = "Admin Hotels")
public class HotelController {
    private final HotelService hotelService;

    @GetMapping("/{id}")
    @Operation(summary = "Get hotel")
    public Result<Hotel> get(@PathVariable Long id) { return Result.success(hotelService.getById(id)); }

    @GetMapping
    @Operation(summary = "List hotels")
    public Result<List<Hotel>> list(@RequestParam(defaultValue = "0") long offset,
                                    @RequestParam(defaultValue = "20") int limit) {
        return Result.success(hotelService.list(offset, limit));
    }

    @PostMapping
    @Operation(summary = "Create hotel")
    public Result<Hotel> create(@Valid @RequestBody Hotel hotel) {
        hotelService.create(hotel);
        return Result.success(hotel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update hotel")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody Hotel hotel) {
        hotel.setId(id);
        hotelService.update(hotel);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete hotel")
    public Result<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return Result.success();
    }

    @GetMapping("/hotels/search-available")
    public Result<List<AvailableHotelVO>> searchAvailableHotels(HotelAvailabilitySearchDTO dto) {
        return Result.success(
                hotelService.searchAvailableHotels(dto)
        );
    }
}
