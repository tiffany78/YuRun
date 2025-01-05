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
import com.example.YuRun.Member.ActivityMember.ActivityMember;
import com.example.YuRun.Member.Progress.ProgressRace;
import com.example.YuRun.Member.Progress.ProgressRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class HomeUserController {
    @Autowired
    private HomeMemberRepo repo;

    @Autowired
    private ProgressRepo recapRepo;

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

        Double sumDistance = 0.0;
        List<String> listDuration = new ArrayList<>();
        List<ActivityMember> runList = this.recapRepo.getAllActivities(id_user, null);
        for(ActivityMember curr : runList){
            sumDistance += curr.getDistance();
            listDuration.add(curr.getDuration());
        }

        List<ProgressRace> listRace = this.recapRepo.getAllRace(id_user);
        model.addAttribute("raceList", list2);

        // Perhitungan total distance dan time dari race
        for(ProgressRace curr : listRace){
            sumDistance += curr.getDistance();
            listDuration.add(curr.getMember_duration());
        }

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        List<Activity> graphList = this.repo.getActivityMonths(firstDayOfMonth, id_user);
        model.addAttribute("graphList", graphList);

        String sumDuration = sumDurations(listDuration);
        model.addAttribute("sumDuration", sumDuration);

        String formattedDistance = String.format("%.2f", sumDistance);
        model.addAttribute("sumDistance", formattedDistance);

        model.addAttribute("sumActivities", runList.size());
        model.addAttribute("sumRace", listRace.size());
        
        return "Member/HomePage/home";
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
