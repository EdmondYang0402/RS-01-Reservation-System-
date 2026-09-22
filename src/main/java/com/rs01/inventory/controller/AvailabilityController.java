package com.rs01.inventory.controller;

import com.rs01.common.result.Result;
import com.rs01.inventory.dto.AvailabilitySearchDTO;
import com.rs01.inventory.service.InventoryService;
import com.rs01.inventory.vo.AvailableRoomTypeVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/availability")
@Tag(name = "Availability")
@SecurityRequirements
@RequiredArgsConstructor
public class AvailabilityController {
    private final InventoryService inventoryService;

    @GetMapping
    @Operation(summary = "Search available room types")
    public Result<List<AvailableRoomTypeVO>> search(@Valid @ModelAttribute AvailabilitySearchDTO request) {
        return Result.success(inventoryService.searchAvailability(request));
    }
}
