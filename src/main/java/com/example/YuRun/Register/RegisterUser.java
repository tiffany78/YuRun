package com.example.YuRun.Register;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUser {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean isadmin;
    private boolean status;
}
