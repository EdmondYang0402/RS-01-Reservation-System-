package com.rs01.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank
    @Size(min = 3, max = 64)
    private String username;
    @NotBlank
    @Size(min = 8, max = 72)
    private String password;
    @NotBlank
    @Size(max = 100)
    private String name;
    @Email
    @Size(max = 255)
    private String email;
    @Size(max = 32)
    private String phone;
}
