package com.example.YuRun.AddActivity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class AddActivityController {

    @GetMapping("/addActivity2")
    public String index2(){
        return "/AddActivity/index2";
    }

    // @GetMapping("/addActivity")
    // public String index(){
    //     return "/AddActivity/index";
    // }
}
