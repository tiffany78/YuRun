package com.example.YuRun.Member.ActivityMember;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.YuRun.RequiredRole;
import com.example.YuRun.Member.HomePage.Activity;
import com.example.YuRun.Member.HomePage.HomeMemberRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class ActivityController {
    @Autowired
    private HomeMemberRepo repo;
    @Autowired
    private AddActivityRepo repoAdd;

    @GetMapping("/activity")
    @RequiredRole("member")
    public String home(HttpSession session, Model model){
        String user = (String) session.getAttribute("username");
        if(user == null){
            user = "Adji Ganteng";
            session.setAttribute("id_user", 3);
        }

        List<Activity> list = this.repo.getActivityAll(user);
        model.addAttribute("activity", list);
        return "Member/Activity/home";
    }

    @GetMapping("/addActivity")
    @RequiredRole("member")
    public String addActivity(){
        return "Member/Activity/addActivity";
    }

    @PostMapping("/addActivity")
    @RequiredRole("member")
    public String addActivity2(
        @RequestParam("distance") Double distance,
        @RequestParam("hour") Integer hour,
        @RequestParam("minute") Integer minute,
        @RequestParam("second") Integer second,
        @RequestParam("date") String date,
        @RequestParam("time") String time,
        @RequestParam("title") String title,
        @RequestParam("desc") String desc,
        @RequestParam(value = "pict", required = false) MultipartFile pict,
        HttpSession session){

        int id_user = 3;
        String duration = String.format("%02d:%02d:%02d", hour, minute, second);
        
        time += ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(time, formatter);
        Time sqlTime = Time.valueOf(localTime);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);

        // Convert file gambar ke byte[]
        byte[] pictBytes = null;
        if (pict != null && !pict.isEmpty()) {
            try {
                pictBytes = pict.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                // Tangani error
            }
        }

        this.repoAdd.addActivity(id_user, title, "null", distance, sqlDate, sqlTime, desc, pictBytes);
        return "redirect:/member/activity";
    }

    @GetMapping("/editActivity/{idActivity}")
    @RequiredRole("member")
    public String editActivity(@PathVariable("idActivity") int idActivity, Model model){
        ActivityMember currAct = this.repoAdd.getById(idActivity);
        model.addAttribute("activity", currAct);

        // Mengonversi gambar menjadi base64
        if (currAct.getPicture() != null) {
            String base64Picture = Base64.getEncoder().encodeToString(currAct.getPicture());
            System.out.println("Base64 Picture: " + base64Picture);  // Debugging output
            model.addAttribute("picture", base64Picture);
        } else {
            model.addAttribute("picture", null);
        }

        return "/Member/Activity/editActivity";
    }

}
