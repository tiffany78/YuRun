package com.example.YuRun.LandingPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
    @GetMapping("/")
    public String index(){
        return "/LandingPage/index";
    }

    @GetMapping("/page")
    public String index2(){
        return "/LandingPage/index2";
    }
}