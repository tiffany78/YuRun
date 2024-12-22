package com.example.YuRun.Member.HomePage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.YuRun.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class HomeUserController {
    @Autowired
    private HomeMemberRepo repo;

    @GetMapping
    @RequiredRole("member")
    public String home(HttpSession session, Model model){
        String user = (String) session.getAttribute("username");
        session.setAttribute("username", user);
        int id_user = (Integer) session.getAttribute("id_user");
        session.setAttribute("id_user", id_user);

        List<Activity> list = this.repo.getActivity(user);
        model.addAttribute("activity", list);
        
        List<RaceUser> list2 = this.repo.getMyRace(user);
        model.addAttribute("races", list2);

        list2 = this.repo.getUpRace(user);
        model.addAttribute("upRaces", list2);
        
        return "Member/HomePage/home";
    }
}
