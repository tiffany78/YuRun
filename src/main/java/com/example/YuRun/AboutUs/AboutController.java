package com.example.YuRun.AboutUs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/AboutUs")
    public String showAboutPage() {
        return "AboutUs/index";
    }
}
