package com.example.YuRun.Member.Progress;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RestController
    public class ChartProgressController {
        @Autowired
        private ProgressRepo repo;

        @GetMapping("/getGraphProgress")
        public Map<String, Object> getChartData(HttpSession session) {
            int id_user = (Integer) session.getAttribute("id_user");
            session.setAttribute("id_user", id_user);

            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Double> distances = new ArrayList<>();

            List<ActivityMember> list = this.repo.getAllActivities(id_user);
            for(ActivityMember currAct : list){
                titles.add(currAct.getTitle());
                distances.add(currAct.getDistance());
            }

            List<ProgressRace> list2 = this.repo.getAllRace(id_user);
            for(ProgressRace curr : list2){
                titles.add(curr.getTitle());
                distances.add(curr.getDistance());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("categories", titles); 
            response.put("data", distances); 

            return response;
        }
    }
}
