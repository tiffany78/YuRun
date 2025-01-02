package com.example.YuRun.Admin.Homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.YuRun.RequiredRole;

@Controller
@RequestMapping("/admin")
public class HomeController {
    @Autowired
    private HomeRepository repo;
    
    @GetMapping("/home")
    @RequiredRole("admin")
    public String index(Model model) {
        List<Race> list = this.repo.findRace();
        model.addAttribute("race", list);
        return "Admin/HomePage/index";
    }

    @RestController
    public class ChartController {
        @Autowired
        private HomeRepository repo;

        @GetMapping("/getGraph1Admin")
        public Map<String, Object> getChartData1() {
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Integer> members = new ArrayList<>();

            List<JoinRace> list2 = this.repo.countRace();
            for(JoinRace currRace : list2){
                titles.add(currRace.getTitle());
                members.add(currRace.getCount());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("categories", titles); 
            response.put("data", members); 

            return response;
        }

        @GetMapping("/getGraph2Admin")
        public Map<String, Object> getChartData2() {
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Integer> members = new ArrayList<>();

            List<JoinRace> list2 = this.repo.countRace();
            for(JoinRace currRace : list2){
                titles.add(currRace.getTitle());
                members.add(currRace.getCount());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("categories", titles); 
            response.put("data", members); 

            return response;
        }
    }
}
