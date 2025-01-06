package com.example.YuRun.LandingPage.LandingPageRace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.YuRun.Admin.Homepage.Race;

@Controller
public class RaceLandingController {
    @Autowired
    private RaceLandingRepository repo;
    
    @GetMapping("/race")
    public String homepageRace (Model model,  @RequestParam(value = "filter", required = false, defaultValue = "") String filter) {
        List<Race>  list = repo.findRace(filter);
        model.addAttribute("raceList", list);
        model.addAttribute("filter", filter);

        return "/LandingPage/raceLandingPage";
    } 
}
