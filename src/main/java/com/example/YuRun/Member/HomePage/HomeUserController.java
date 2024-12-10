package com.example.YuRun.Member.HomePage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class HomeUserController {
    @Autowired
    private HomeMemberRepo repo;

    @GetMapping
    public String home(HttpSession session, Model model){
        String user = (String) session.getAttribute("username");

        List<Activity> list = this.repo.getActivity(user);
        System.out.println(list.size());
        model.addAttribute("activity", list);
        System.out.println(list.get(0).getDuration());
        return "Member/HomePage/home";
    }
}
