package com.example.YuRun.Member.Progress;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        Double sumDistance = 0.0;
        List<String> listDuration = new ArrayList<>();

        // Konversi startDate dan endDate ke tipe java.sql.Date
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            if (startDateStr != null && endDateStr != null) {    
                startDate = Date.valueOf(startDateStr);
                endDate = Date.valueOf(endDateStr);

                startDateStr = formatter.format(startDate);
                endDateStr = formatter.format(endDate);
            }
        } catch (IllegalArgumentException e) {
            startDate = null;
            endDate = null;
        }

        List<ActivityMember> runList;
        List<ProgressRace> raceList;
        if (startDate != null && endDate != null) {
            runList = this.repo.getActivitiesByDateRange(id_user, startDate, endDate);
            raceList = this.repo.getRaceByDateRange(id_user, startDate, endDate);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);

            String fullDate = startDateStr + " - " + endDateStr;
            model.addAttribute("currFilter", fullDate);
        } else {
            // Gunakan filterType untuk menentukan logika lainnya
            switch (filterType) {
                case "Weekly":
                    runList = this.repo.getWeeklyActivities(id_user);
                    raceList = this.repo.getWeeklyRace(id_user);
                    model.addAttribute("currFilter", "Weekly");
                    break;
                case "1Month":
                    runList = this.repo.getMonthlyActivities(id_user);
                    raceList = this.repo.getMonthlyRace(id_user);
                    model.addAttribute("currFilter", "1 Month");
                    break;
                case "3Months":
                    runList = this.repo.getThreeMonthlyActivities(id_user);
                    raceList = this.repo.getThreeMonthlyRace(id_user);
                    model.addAttribute("currFilter", "3 Month");
                    break;
                case "1Year":
                    runList = this.repo.getYearlyActivities(id_user);
                    raceList = this.repo.getYearlyRace(id_user);
                    model.addAttribute("currFilter", "1 Year");
                    break;
                case "All":
                default:
                    runList = this.repo.getAllActivities(id_user);
                    raceList = this.repo.getAllRace(id_user);
                    model.addAttribute("currFilter", "All");
            }
        }
        model.addAttribute("runList", runList);
        model.addAttribute("raceList", raceList);

        // Perhitungan total distance dan time dari running
        for(ActivityMember curr : runList){
            sumDistance += curr.getDistance();
            listDuration.add(curr.getDuration());
        }

        // Perhitungan total distance dan time dari race
        for(ProgressRace curr : raceList){
            sumDistance += curr.getDistance();
            listDuration.add(curr.getMember_duration());
        }

        String sumDuration = sumDurations(listDuration);
        model.addAttribute("sumDuration", sumDuration);

        String formattedDistance = String.format("%.2f", sumDistance);
        model.addAttribute("sumDistance", formattedDistance);

        return "Member/Progress/index";
    }

    public static String sumDurations(List<String> durations) {
        int totalSeconds = 0;

        // Konversi setiap durasi ke detik dan tambahkan
        for (String duration : durations) {
            if(duration != null){
                String[] parts = duration.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                int seconds = Integer.parseInt(parts[2]);
    
                totalSeconds += (hours * 3600) + (minutes * 60) + seconds;
            }
        }

        // Konversi total detik kembali ke HH:mm:ss
        int hours = totalSeconds / 3600;
        int remainingSeconds = totalSeconds % 3600;
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        return String.format("%02d Hours %02d Minutes %02d Seconds", hours, minutes, seconds);
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
            List<ProgressRace> raceList;
            if (startDate != null && endDate != null) {
                activityList = this.repo.getActivitiesByDateRange(id_user, startDate, endDate);
                raceList = this.repo.getRaceByDateRange(id_user, startDate, endDate);
            } else {
                switch (filterType) {
                    case "Weekly":
                        activityList = this.repo.getWeeklyActivities(id_user);
                        raceList = this.repo.getWeeklyRace(id_user);
                        break;
                    case "1Month":
                        activityList = this.repo.getMonthlyActivities(id_user);
                        raceList = this.repo.getMonthlyRace(id_user);
                        break;
                    case "3Months":
                        activityList = this.repo.getThreeMonthlyActivities(id_user);
                        raceList = this.repo.getThreeMonthlyRace(id_user);
                        break;
                    case "1Year":
                        activityList = this.repo.getYearlyActivities(id_user);
                        raceList = this.repo.getYearlyRace(id_user);
                        break;
                    case "All":
                    default:
                        activityList = this.repo.getAllActivities(id_user);
                        raceList = this.repo.getAllRace(id_user);
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
