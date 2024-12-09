package com.example.YuRun.AddActivity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddActivityController {

    @GetMapping("/addActivity")
    public String showAddActivityPage(){
        return "/AddActivity/AddActivityPage";
    }
}
