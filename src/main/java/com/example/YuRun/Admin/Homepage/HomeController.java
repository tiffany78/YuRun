package com.example.YuRun.Admin.Homepage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.YuRun.RequiredRole;

@Controller
@RequestMapping("/admin")
public class HomeController {
    @Autowired
    private HomeRepository repo;
    
    @GetMapping("/home")
    @RequiredRole("admin")
    public String index(Model model){
        List<Race> list = this.repo.findRace();
        model.addAttribute("race", list);

        List<JoinRace> list2 = this.repo.countRace();
        Map<String, Integer> mapJoin = new LinkedHashMap<>();
        for(JoinRace currRace : list2){
            mapJoin.put(currRace.getTitle(), currRace.getCount());
        }
        model.addAttribute("surveyMap", mapJoin);
        return "Admin/HomePage/index";
    }

    @GetMapping("/displayLineGraph")
    @RequiredRole("admin")
    public String lineChart(){
        return "Admin/HomePage/lineGraph";
    }
}
