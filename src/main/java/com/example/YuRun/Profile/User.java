package com.example.YuRun.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String email;
    private String password;
    private Boolean isAdmin;  // Kolom isAdmin
    private Boolean status;   // Kolom status
}
