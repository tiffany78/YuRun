package com.example.YuRun.Admin.RaceService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    public String index(
        Model model,
        @RequestParam(required = false, defaultValue = "") String filter,
        @RequestParam(required = false, defaultValue = "null") String statusRace,
        @RequestParam(value = "entries", required = false, defaultValue = "0") int entries,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page){

        // Hitung offset berdasarkan halaman dan jumlah entri
        int offset = (page - 1) * entries;

        List<CountRace> list = this.repo.findRace(filter, statusRace, entries, offset);
        model.addAttribute("raceList", list);
        model.addAttribute("filter", filter);
        model.addAttribute("statusRace", statusRace);

        int totalEntries = this.repo.getTotalEntries(filter, statusRace);
        if (entries == 0) {
            entries = totalEntries;
        }
        int currEntries = totalEntries;
        if (entries > 0){
            currEntries = Math.min(page * entries, totalEntries);
        }
        model.addAttribute("entries", entries);
        model.addAttribute("currEntries", currEntries);
        model.addAttribute("totalEntries", totalEntries);
        model.addAttribute("currentPage", page);

        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("currentDate", now);

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
        @RequestParam("date") String date,
        @RequestParam("distance") Double distance,
        @RequestParam("desc") String desc) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);

        this.repo.addRace(title, sqlDate, distance, desc);

        return "redirect:/admin/race";
    }

    @GetMapping("/editRace/{idRace}")
    @RequiredRole("admin")
    public String editRace(@PathVariable("idRace") int idRace, Model model) {
        Race race = this.repo.getById(idRace);
        model.addAttribute("race", race);
        return "/Admin/Race/editRace";
    }

    @PostMapping("/editRace/{idRace}")
    @RequiredRole("admin")
    public String updateRace(
        @RequestParam("title") String title,
        @RequestParam("endDate") String date,
        @RequestParam("distance") Double distance,
        @RequestParam("description") String desc,
        @PathVariable("idRace") int idRace
    ){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Date sqlDate = Date.valueOf(localDate);

            this.repo.updateRace(title, sqlDate, distance, desc, idRace);

            return "redirect:/admin/race";
    }

    @GetMapping("/race/approval/{idRace}")
    @RequiredRole("admin")
    public String indexApproval(@PathVariable("idRace") int idRace, Model model,
    @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
    @RequestParam(value = "statusMember", required = false, defaultValue = "") String statusMember){
        List<ResultRace> list = this.repo.getAllResultRace(idRace, filter, statusMember);

        Map<String, Double> result = this.repo.getTitleAndDistance(idRace);
        result.forEach((title, distance) -> {
            model.addAttribute("title", title);
            model.addAttribute("target", distance);
        });

        boolean status = this.repo.getRaceStatus(idRace);
        model.addAttribute("statusRace", status);
        model.addAttribute("filter", filter);
        model.addAttribute("statusMember", statusMember);
        model.addAttribute("idRace", idRace);
        model.addAttribute("resultRace", list);
        
        if (list.isEmpty() || list.size() == 0) {
            model.addAttribute("message", "No member found for the given filter.");
        }
        else{
            if(status) {
                String name = this.repo.getWinner(idRace);
                model.addAttribute("winner", name);
            }
            else {
                model.addAttribute("winner", list.get(0).getName());
            }
        }

        return "/Admin/Race/approval";
    }

    @PostMapping("/race/approval/{idRace}")
    public String saveApproval(
        @PathVariable("idRace") int idRace,
        @RequestParam("id_users") List<Integer> idUsers,
        @RequestParam("statuses") List<String> statuses) {

        for (int i = 0; i < idUsers.size(); i++) {
            int idUser = idUsers.get(i);
            String status = statuses.get(i);

            boolean statusValue = false;
            if ("true".equals(status)) {
                statusValue = true;
            }

            this.repo.updateStatus(idRace, idUser, statusValue);
        }
        this.repo.updateStatusRace(idRace);
        this.repo.setWinner(idRace);

        return "redirect:/admin/race";
    }

    @GetMapping("/race/close/{idRace}")
    @RequiredRole("admin")
    public String deleteRace(@PathVariable int idRace){
        this.repo.deleteRace(idRace);
        return "redirect:/admin/race";
    }
}
