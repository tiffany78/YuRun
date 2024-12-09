package com.example.YuRun.Register;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class RegisterController {
    @GetMapping("/register")
    public String register(){
        return "/Register/index";
    }
}
