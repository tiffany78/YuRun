package com.example.YuRun.Member.Progress;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.YuRun.Member.ActivityMember.ActivityMember;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class ProgressController {
    @Autowired
    private ProgressRepo repo;

    @GetMapping("/progress")
    public String index(
        @RequestParam(value = "filterType", required = false, defaultValue = "All") String filterType,
        @RequestParam(value = "startDate", required = false) String startDateStr,
        @RequestParam(value = "endDate", required = false) String endDateStr,
        HttpSession session,
        Model model) {
        
        int id_user = (Integer) session.getAttribute("id_user");
        session.setAttribute("id_user", id_user);

        // Konversi startDate dan endDate ke tipe java.sql.Date
        Date startDate = null;
        Date endDate = null;

        try {
            if (startDateStr != null && endDateStr != null) {
                startDate = Date.valueOf(startDateStr); // Konversi String ke java.sql.Date
                endDate = Date.valueOf(endDateStr);
            }
        } catch (IllegalArgumentException e) {
            // Jika format tanggal salah, kembalikan ke default
            startDate = null;
            endDate = null;
        }

        List<ActivityMember> runList;
        if (startDate != null && endDate != null) {
            runList = this.repo.getActivitiesByDateRange(id_user, startDate, endDate);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        } else {
            // Gunakan filterType untuk menentukan logika lainnya
            switch (filterType) {
                case "Weekly":
                    runList = this.repo.getWeeklyActivities(id_user);
                    break;
                case "1Month":
                    runList = this.repo.getMonthlyActivities(id_user);
                    break;
                case "3Months":
                    runList = this.repo.getThreeMonthlyActivities(id_user);
                    break;
                case "1Year":
                    runList = this.repo.getYearlyActivities(id_user);
                    break;
                case "All":
                default:
                    runList = this.repo.getAllActivities(id_user);
            }
        }

        model.addAttribute("runList", runList);

        List<ProgressRace> list2 = this.repo.getAllRace(id_user);
        model.addAttribute("raceList", list2);

        return "Member/Progress/index";
    }

    @RestController
    public class ChartProgressController {
        @Autowired
        private ProgressRepo repo;
    
        @GetMapping("/getGraphProgress")
        public Map<String, Object> getChartData(
            @RequestParam(value = "filterType", required = false, defaultValue = "All") String filterType,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            HttpSession session) {
            
            int id_user = (Integer) session.getAttribute("id_user");
            session.setAttribute("id_user", id_user);
    
            // Konversi startDate dan endDate ke tipe java.sql.Date
            Date startDate = null;
            Date endDate = null;
            try {
                if (startDateStr != null && endDateStr != null) {
                    startDate = Date.valueOf(startDateStr);
                    endDate = Date.valueOf(endDateStr);
                }
            } catch (IllegalArgumentException e) {
                startDate = null;
                endDate = null;
            }
    
            // Filter data berdasarkan filterType atau rentang tanggal
            List<ActivityMember> activityList;
            if (startDate != null && endDate != null) {
                activityList = this.repo.getActivitiesByDateRange(id_user, startDate, endDate);
            } else {
                switch (filterType) {
                    case "Weekly":
                        activityList = this.repo.getWeeklyActivities(id_user);
                        break;
                    case "1Month":
                        activityList = this.repo.getMonthlyActivities(id_user);
                        break;
                    case "3Months":
                        activityList = this.repo.getThreeMonthlyActivities(id_user);
                        break;
                    case "1Year":
                        activityList = this.repo.getYearlyActivities(id_user);
                        break;
                    case "All":
                    default:
                        activityList = this.repo.getAllActivities(id_user);
                }
            }
    
            // Siapkan data untuk grafik
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<Double> distances = new ArrayList<>();
    
            for (ActivityMember activity : activityList) {
                titles.add(activity.getTitle());
                distances.add(activity.getDistance());
            }
    
            // Tambahkan data dari race (opsional, sesuai kebutuhan)
            List<ProgressRace> raceList = this.repo.getAllRace(id_user);
            for (ProgressRace race : raceList) {
                titles.add(race.getTitle());
                distances.add(race.getDistance());
            }
    
            // Kembalikan data sebagai JSON
            Map<String, Object> response = new HashMap<>();
            response.put("categories", titles);
            response.put("data", distances);
    
            return response;
        }
    }    
}
