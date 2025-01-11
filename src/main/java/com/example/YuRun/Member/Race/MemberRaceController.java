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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.YuRun.RequiredRole;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberRaceController {
    private final MemberRaceService raceService;
    private MemberRaceRepository raceRepository;
    
    @Autowired
    public MemberRaceController(MemberRaceService raceService, MemberRaceRepository raceRepository) {
        this.raceService = raceService;
        this.raceRepository = raceRepository;
    }

    @GetMapping("/race")
    public String race(Model model, 
                    HttpSession session,
                    @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                    @RequestParam(value = "sort", required = false, defaultValue = "null") String sort,
                    @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        Integer currentUserId = (Integer) session.getAttribute("id_user");
        
        if (currentUserId != null) {
            // Get filtered and sorted races for the user
            List<Race> races = raceService.getAvailableRaces(currentUserId, filter, sort, status);
            model.addAttribute("races", races);

            Map<Integer, Boolean> raceStatuses = new HashMap<>();
            for (Race race : races) {
                boolean isJoined = raceService.isUserJoinedRace(race.getId_race(), currentUserId);
                raceStatuses.put(race.getId_race(), isJoined);
            }

            Map<Integer, Boolean> uploadStatus = new HashMap<>();
            for (Race race : races) {
                boolean isUpload = raceService.isUserUpload(race.getId_race(), currentUserId);
                uploadStatus.put(race.getId_race(), isUpload);
            }

            LocalDateTime now = LocalDateTime.now();
            model.addAttribute("currentDate", now);

            model.addAttribute("currentUserId", currentUserId);
            model.addAttribute("raceStatuses", raceStatuses);
            model.addAttribute("uploadStatus", uploadStatus);
            model.addAttribute("filter", filter);
            model.addAttribute("sort", sort);
            model.addAttribute("status", status);
        } else {
            model.addAttribute("races", new ArrayList<>());
            model.addAttribute("currentUserId", 0);
            model.addAttribute("raceStatuses", new HashMap<>());
            model.addAttribute("uploadStatus", new HashMap<>());
            model.addAttribute("filter", "");
            model.addAttribute("sort", "null");
            model.addAttribute("status", "null");
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

    @PostMapping("/race/selectRace")
    public String selectRace(@RequestParam("id_race") int idRace, HttpSession session) {
        session.setAttribute("selected_race_id", idRace);
        return "redirect:/member/uploadRace";
    }

    @GetMapping("/uploadRace")
    @RequiredRole("member")
    public String uploadRace(Model model, HttpSession session) {
        Integer idRace = (Integer) session.getAttribute("selected_race_id");
        if (idRace == null) {
            return "redirect:/member/race";
        }
        
        Race race = raceService.getRaceById(idRace);
        model.addAttribute("race", race);
        return "/Member/Race/uploadRace";
    }

    @PostMapping("/uploadRace")
    @RequiredRole("member")
    public String uploadRace2(
        @RequestParam("hour") Integer hour,
        @RequestParam("minute") Integer minute,
        @RequestParam("second") Integer second,
        @RequestParam("fileImage") MultipartFile fileImage,
        HttpSession session,
        RedirectAttributes redirectAttributes) throws IOException {
        
        Integer idUserObj = (Integer) session.getAttribute("id_user");
        Integer idRace = (Integer) session.getAttribute("selected_race_id");
        
        if (idUserObj == null || idRace == null) {
            return "/ErrorLogin/errorPage";
        }
        
        int id_user = idUserObj;
        String duration = String.format("%02d:%02d:%02d", hour, minute, second);
        
        String fileName = null;
        if (fileImage != null && !fileImage.isEmpty()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            fileName = "idUser_" + id_user + "_" + timestamp + ".jpg";
            String uploadDir = "upload/RaceActivity-member";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = fileImage.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not save file: " + fileName, e);
            }
        }

        // Save to database and update status
        raceService.saveRaceActivity(idRace, id_user, duration, fileName);
        
        session.removeAttribute("selected_race_id");
        redirectAttributes.addFlashAttribute("successMessage", "Race activity has been saved successfully!");
        
        return "redirect:/member/race";
    }

    @GetMapping("/raceActivityHistory")
    @RequiredRole("member")
    public String raceActivityHistory(HttpSession session, Model model,
        @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
        @RequestParam(value = "sort", required = false, defaultValue = "null") String sort,
        @RequestParam(value = "status", required = false, defaultValue = "") String status) {

        Integer idUserObj = (Integer) session.getAttribute("id_user");
        if (idUserObj == null) {
            return "/ErrorLogin/errorPage";
        }

        // Dapatkan daftar aktivitas race
        List<RaceActivity> activities = raceRepository.getRaceActivities(idUserObj, filter, sort, status);

        // Kirim data ke view
        model.addAttribute("activity", activities);
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        model.addAttribute("status", status);

        return "Member/Race/raceActivityHistory";
    }
}