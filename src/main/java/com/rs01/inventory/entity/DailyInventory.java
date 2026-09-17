package com.rs01.inventory.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DailyInventory {
    private Long id;
    private Long roomTypeId;
    private LocalDate stayDate;
    private Integer totalInventory;
    private Integer reservedCount;
    private Integer outOfServiceCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
