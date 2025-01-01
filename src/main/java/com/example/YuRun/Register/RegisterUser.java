package com.example.YuRun.Register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUser {
    @NotBlank
    @Size(min = 4, max = 30, message 
      = "Name must be between 4 and 30 characters.")
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, message = "Password must have at least 4 characters.")
    private String password;

    @NotBlank
    @Size(min = 4, message = "Confirm password must have at least 4 characters.")
    private String confirmPassword;
}
