package com.example.YuRun.Member.Progress;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.YuRun.Member.ActivityMember.ActivityMember;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class ProgressController {
    @Autowired
    private ProgressRepo repo;

    @GetMapping("/progress")
    public String index(HttpSession session, Model model){
        int id_user = (Integer) session.getAttribute("id_user");
        session.setAttribute("id_user", id_user);

        List<ActivityMember> list = this.repo.getAllActivities(id_user);
        model.addAttribute("runList", list);

        List<ProgressRace> list2 = this.repo.getAllRace(id_user);
        model.addAttribute("raceList", list2);

        return "Member/Progress/index";
    }
}
