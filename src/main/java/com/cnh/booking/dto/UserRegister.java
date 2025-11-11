package com.cnh.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String gender;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
