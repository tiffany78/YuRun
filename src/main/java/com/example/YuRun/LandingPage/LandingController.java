package com.example.YuRun.LandingPage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.YuRun.Admin.Homepage.HomeRepository;
import com.example.YuRun.Admin.Homepage.Race;


@Controller
public class LandingController {
    @Autowired
    private HomeRepository repo;

    @GetMapping("/")
    public String index(Model model){
        List<Race> listRace = this.repo.getRaceLandingPage();
        model.addAttribute("listRace", listRace);
        return "/LandingPage/index";
    }

    @GetMapping("/AboutUs")
    public String showAboutPage() {
        return "/LandingPage/aboutUs";
    }
}
