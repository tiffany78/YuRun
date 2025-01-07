package com.example.YuRun.Member.Race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberRaceController {
    private final MemberRaceService raceService;
   
    @Autowired
    public MemberRaceController(MemberRaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/race")
    public String race(Model model, HttpSession session) {
        // Get races
        List<Race> races = raceService.getAllRaces();
        model.addAttribute("races", races);

        // Get current user from session
        Integer currentUserId = (Integer) session.getAttribute("id_user");

        if (currentUserId != null) {
            // Get race statuses
            Map<Integer, Boolean> raceStatuses = new HashMap<>();
            for (Race race : races) {
                boolean isJoined = raceService.isUserJoinedRace(race.getId_race(), currentUserId);
                raceStatuses.put(race.getId_race(), isJoined);
            }

            model.addAttribute("currentUserId", currentUserId);
            model.addAttribute("raceStatuses", raceStatuses);
        } else {
            model.addAttribute("currentUserId", 0);
            model.addAttribute("raceStatuses", new HashMap<>());
        }

        return "/Member/Race/index";
    }

    @PostMapping("/race/join")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> joinRace(@RequestParam int id_race, @RequestParam int id_user) {
        boolean success = raceService.joinRace(id_race, id_user);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        if (success) {
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to join the race.");
            System.out.println("Failed to join race for id_race: " + id_race + " id_user: " + id_user); // Add logging
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/race/exit")
    @ResponseBody
    public Map<String, Object> exitRace(@RequestParam int id_race, @RequestParam int id_user) {
        raceService.exitRace(id_race, id_user);
        return Map.of("success", true);
    }

    @GetMapping("/uploadRace")
    public String uploadIndex() {
        return "/Member/Race/uploadRace";
    }
}