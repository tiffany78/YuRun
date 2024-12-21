package com.example.YuRun.Admin.RaceService;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.YuRun.RequiredRole;
import com.example.YuRun.Admin.Homepage.Race;

@Controller
@RequestMapping("/admin")
public class RaceController {
    @Autowired
    private RaceRepository repo;

    @GetMapping("/race")
    @RequiredRole("admin")
    public String index(Model model){
        List<Race> list = this.repo.findRace();
        model.addAttribute("raceList", list);
        return "/Admin/Race/home";
    }

    @GetMapping("/addRace")
    @RequiredRole("admin")
    public String index2(){
        return "/Admin/Race/addRace";
    }

    @PostMapping("/addRace")
    @RequiredRole("admin")
    public String addRace(
        @RequestParam("title") String title,
        @RequestParam("time") String time,
        @RequestParam("date") String date,
        @RequestParam("distance") Double distance,
        @RequestParam("desc") String desc) {

        time += ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(time, formatter);
        Time sqlTime = Time.valueOf(localTime);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);

        this.repo.addRace(title, sqlTime, sqlDate, distance, desc);

        return "redirect:/admin/race";
    }

    @GetMapping("/editRace/{idRace}")
    @RequiredRole("admin")
    public String editRace(@PathVariable("idRace") int idRace, Model model) {
        Race race = this.repo.getById(idRace);
        model.addAttribute("race", race); // Tambahkan objek race
        return "/Admin/Race/editRace";
    }

    @PostMapping("/editRace/{idRace}")
    @RequiredRole("admin")
    public String updateRace(
        @RequestParam("title") String title,
        @RequestParam("time") String time,
        @RequestParam("startDate") String date,
        @RequestParam("distance") Double distance,
        @RequestParam("description") String desc,
        @PathVariable("idRace") int idRace
    ){
        time += ":00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(time, formatter);
            Time sqlTime = Time.valueOf(localTime);

            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Date sqlDate = Date.valueOf(localDate);

            this.repo.updateRace(title, sqlTime, sqlDate, distance, desc, idRace);

            return "redirect:/admin/race";
    }
}
