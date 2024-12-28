package com.example.YuRun.Register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUser {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 4)
    private String password;
    @NotBlank
    @Size(min = 4)
    private String confirmPassword;

    
    private boolean isadmin;
    private boolean status;
}
