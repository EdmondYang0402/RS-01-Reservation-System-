package com.rs01.common;

import com.rs01.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("RS-01 backend is running");
    }
}
