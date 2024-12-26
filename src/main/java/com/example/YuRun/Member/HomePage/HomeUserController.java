package com.example.YuRun.Member.HomePage;

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

    @RestController
    public class ChartController {
        @Autowired
        private HomeMemberRepo repo;

        @GetMapping("/getGraph1")
        public Map<String, Object> getChartData(HttpSession session) {
            int id_user = (Integer) session.getAttribute("id_user");
            session.setAttribute("id_user", id_user);

            LocalDate today = LocalDate.now();
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Double> distances = new ArrayList<>();

            List<Activity> list = this.repo.getActivityMonths(firstDayOfMonth, id_user);
            for(Activity act : list){
                titles.add(act.getTitle());
                distances.add(act.getDistance());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("categories", titles); 
            response.put("data", distances); 

            return response;
        }
    }
}
