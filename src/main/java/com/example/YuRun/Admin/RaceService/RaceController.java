package com.example.YuRun.Admin.RaceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.YuRun.Admin.Homepage.Race;

@Controller
@RequestMapping("/admin")
public class RaceController {
    @Autowired
    private RaceRepository repo;

    @GetMapping("/race")
    public String index(Model model){
        List<Race> list = this.repo.findRace();
        model.addAttribute("raceList", list);
        System.out.println(list);

        return "/Admin/Race/home";
    }

    @GetMapping("/addRace")
    public String index2(){
        return "/Admin/Race/addRace";
    }
}
