package com.rs01.auth;

import com.rs01.auth.dto.LoginDTO;
import com.rs01.auth.dto.RegisterDTO;
import com.rs01.auth.vo.LoginVO;
import com.rs01.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register customer")
    public Result<Long> register(@Valid @RequestBody RegisterDTO request) {
        return Result.success(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and obtain JWT")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO request) {
        return Result.success(authService.login(request));
    }
}
