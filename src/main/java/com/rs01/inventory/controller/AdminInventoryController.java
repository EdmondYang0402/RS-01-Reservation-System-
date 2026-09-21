package com.rs01.inventory.controller;

import com.rs01.common.result.Result;
import com.rs01.inventory.dto.InventoryInitializeDTO;
import com.rs01.inventory.entity.DailyInventory;
import com.rs01.inventory.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
@RequiredArgsConstructor
@Tag(name = "Admin Inventory")
@Validated
public class AdminInventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @Operation(summary = "List daily inventory")
    public Result<List<DailyInventory>> list(
            @RequestParam @Positive Long roomTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return Result.success(inventoryService.getInventory(roomTypeId, startDate, endDate));
    }

    @PostMapping("/initialize")
    @Operation(summary = "Initialize missing daily inventory")
    public Result<Integer> initialize(@Valid @RequestBody InventoryInitializeDTO request) {
        return Result.success(inventoryService.initializeInventory(request));
    }
}
