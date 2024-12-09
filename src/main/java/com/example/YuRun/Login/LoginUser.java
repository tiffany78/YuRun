package com.example.YuRun.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
    private String name;
    private String email;
    private String password;
    private String isadmin;
    private boolean status;
}
