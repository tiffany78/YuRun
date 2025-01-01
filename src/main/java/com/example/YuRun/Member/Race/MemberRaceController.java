package com.example.YuRun.Member.Race;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberRaceController {
    @GetMapping("/race")
    public String race() {
        return "/Member/Race/index";
    }

    @GetMapping("/uploadRace")
    public String uploadIndex(){
        return "/Member/Race/uploadRace";
    }
    
}
    