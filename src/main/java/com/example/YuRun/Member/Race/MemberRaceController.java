package com.example.YuRun.Member.Race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.YuRun.Login.LoginService;
import com.example.YuRun.Login.LoginUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberRaceController {
    private MemberRaceService raceService;
    private final LoginService loginService;
    
    @Autowired
    public MemberRaceController(MemberRaceService raceService, LoginService loginService) {
        this.raceService = raceService;
        this.loginService = loginService;
    }

    @GetMapping("/race")
    public String race(Model model) {
        List<Race> races = raceService.getAllRaces();
        model.addAttribute("races", races);
        return "/Member/Race/index";
    }

    // @GetMapping("/race")
    // public String race(Model model) {
    //     try {
    //         // 1. Get races
    //         List<Race> races = raceService.getAllRaces();
    //         model.addAttribute("races", races);
            
    //         try {
    //             // 2. Get current user
    //             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //             System.out.println("Auth: " + auth); // Debug log
                
    //             LoginUser user = loginService.login(auth.getName(), "", model);
    //             System.out.println("User: " + user); // Debug log
                
    //             if (user != null) {
    //                 int currentUserId = user.getId_user();
                    
    //                 // 3. Get race statuses
    //                 Map<Integer, Boolean> raceStatuses = new HashMap<>();
    //                 for (Race race : races) {
    //                     boolean isJoined = raceService.isUserJoinedRace(race.getIdRace(), currentUserId);
    //                     raceStatuses.put(race.getIdRace(), isJoined);
    //                 }
                    
    //                 model.addAttribute("currentUserId", currentUserId);
    //                 model.addAttribute("raceStatuses", raceStatuses);
    //             } else {
    //                 // Handle case when user is null
    //                 System.out.println("User is null"); // Debug log
    //                 model.addAttribute("currentUserId", 0);
    //                 model.addAttribute("raceStatuses", new HashMap<>());
    //             }
                
    //         } catch (Exception e) {
    //             // Handle authentication error
    //             System.err.println("Authentication error: " + e.getMessage());
    //             e.printStackTrace();
    //             model.addAttribute("currentUserId", 0);
    //             model.addAttribute("raceStatuses", new HashMap<>());
    //         }
            
    //         return "/Member/Race/index";
            
    //     } catch (Exception e) {
    //         System.err.println("General error: " + e.getMessage());
    //         e.printStackTrace();
    //         model.addAttribute("error", e.getMessage());
    //         return "error";
    //     }
    // }

    @PostMapping("/race/join")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> joinRace(@RequestParam int idRace, @RequestParam int idUser) {
        boolean success = raceService.joinRace(idRace, idUser);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        if (success) {
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Failed to join the race.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/race/exit")
    @ResponseBody
    public Map<String, Object> exitRace(@RequestParam int idRace, @RequestParam int idUser) {
        raceService.exitRace(idRace, idUser);
        return Map.of("success", true);
    }

    @GetMapping("/uploadRace")
    public String uploadIndex() {
        return "/Member/Race/uploadRace";
    }
}