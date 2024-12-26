package com.example.YuRun.Member.Progress;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class ProgressController {
    @GetMapping("/progress")
    public String index(){
        return "Member/Progress/index";
    }
}
