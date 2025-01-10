package com.example.YuRun.Admin.Homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping("/admin")
    public class ChartController {

        @Autowired
        private HomeRepository repo;

        @GetMapping("/getGraph1Admin")
        public Map<String, Object> getChartData1() {
            List<JoinRace> raceData = repo.countRace();
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Integer> members = new ArrayList<>();

            for (JoinRace race : raceData) {
                titles.add(race.getTitle());
                members.add(race.getCount());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("categories", titles);
            response.put("data", members);

            return response;
        }

        @GetMapping("/getGraph2Admin")
        public Map<String, Object> getMonthlyActivities() {
            List<MonthlyActivity> activities = repo.getMonthlyActivities();
            ArrayList<String> months = new ArrayList<>();
            ArrayList<Integer> counts = new ArrayList<>();

            for (MonthlyActivity activity : activities) {
                months.add(activity.getMonth());
                counts.add(activity.getActivityCount());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("categories", months);
            response.put("data", counts);

            return response;
        }

        @GetMapping("/getGraph3Admin")
        public Map<String, Object> getActivityTypeData() {
            List<ActivityTypeCount> activityCounts = repo.getActivityTypeCounts();
            ArrayList<String> types = new ArrayList<>();
            ArrayList<Map<String, Object>> pieData = new ArrayList<>();

            for (ActivityTypeCount count : activityCounts) {
                types.add(count.getActivityType());
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("name", count.getActivityType());
                dataPoint.put("y", count.getCount());
                pieData.add(dataPoint);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("types", types);
            response.put("data", pieData);

            return response;
        }
    }
}