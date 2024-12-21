package com.example.YuRun.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
    private String name;
    private String email;
    private String password;
    private int isadmin;
    private boolean isNotBanned;
    private int id_user;
}
