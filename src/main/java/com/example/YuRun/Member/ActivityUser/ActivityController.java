package com.example.YuRun.Member.ActivityUser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.YuRun.Member.HomePage.Activity;
import com.example.YuRun.Member.HomePage.HomeMemberRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class ActivityController {
    @Autowired
    private HomeMemberRepo repo;

    @GetMapping("/activity")
    public String home(HttpSession session, Model model){
        String user = (String) session.getAttribute("username");
        if(user == null){
            user = "Adji Ganteng";
        }

        List<Activity> list = this.repo.getActivityAll(user);
        model.addAttribute("activity", list);
        return "Member/Activity/home";
    }

    @GetMapping("/addActivity")
    public String addActivity(){
        return "/AddActivity/index";
    }
}
