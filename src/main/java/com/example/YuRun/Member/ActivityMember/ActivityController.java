package com.example.YuRun.Member.ActivityMember;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class ActivityController {
    @Autowired
    private AddActivityRepo repoAdd;

    @GetMapping("/activity")
    @RequiredRole("member")
    public String home(HttpSession session, Model model){
        String user = (String) session.getAttribute("username");
        session.setAttribute("username", user);
        int id_user = (Integer) session.getAttribute("id_user");
        session.setAttribute("id_user", id_user);

        List<ActivityMember> list = this.repoAdd.getAllActivityMember(id_user);
        model.addAttribute("activity", list);
        return "Member/Activity/home";
    }

    @GetMapping("/addActivity")
    @RequiredRole("member")
    public String addActivity(HttpSession session){
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
        @RequestParam("kind") String kind,
        @RequestParam(value = "pict", required = false) MultipartFile pict,
        @RequestParam("fileImage") MultipartFile fileImage,
        HttpSession session) throws IOException{

        int id_user = (Integer) session.getAttribute("id_user");
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

        String timestamp = String.valueOf(System.currentTimeMillis());
        String fileName = "idUser_" + id_user + "_" + timestamp + ".jpg";
        String uploadDir = "upload/activity-member";
        Path uploadPath = Paths.get(uploadDir);

        // Buat direktori jika belum ada
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = fileImage.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }

        this.repoAdd.addActivity(id_user, title, kind, distance, duration, sqlDate, sqlTime, desc, pictBytes, fileName);
        return "redirect:/member/activity";
    }

    @GetMapping("/editActivity/{idActivity}")
    @RequiredRole("member")
    public String editActivity(@PathVariable("idActivity") int idActivity, Model model){
        ActivityMember currAct = this.repoAdd.getById(idActivity);
        model.addAttribute("activity", currAct);

        // Mengkonversi bagian durasi
        Integer hourInt = Integer.parseInt(currAct.getDuration().substring(0, 2));
        Integer minInt = Integer.parseInt(currAct.getDuration().substring(3, 5));
        Integer secInt = Integer.parseInt(currAct.getDuration().substring(6));

        model.addAttribute("hour", hourInt);
        model.addAttribute("minute", minInt);
        model.addAttribute("second", secInt);

        // Mengonversi gambar menjadi base64
        if (currAct.getPicture() != null) {
            String base64Picture = Base64.getEncoder().encodeToString(currAct.getPicture());
            model.addAttribute("picture", base64Picture);
        } else {
            model.addAttribute("picture", null);
        }

        return "/Member/Activity/editActivity";
    }

    @PostMapping("/editActivity/{idActivity}")
    public String updateActivity(
    @RequestParam("distance") Double distance,
    @RequestParam("hour") Integer hour,
    @RequestParam("minute") Integer minute,
    @RequestParam("second") Integer second,
    @RequestParam("date") String date,
    @RequestParam("time") String time,
    @RequestParam("title") String title,
    @RequestParam("desc") String desc,
    @RequestParam("kind") String kind,
    @RequestParam(value = "pict", required = false) MultipartFile pict,
    @PathVariable("idActivity") int idActivity,
    @RequestParam(value = "pictureOld", required = false) String pictureOldBase64) {

    String duration = String.format("%02d:%02d:%02d", hour, minute, second);
    
    // Konversi waktu ke SQL Time
    time += ":00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalTime localTime = LocalTime.parse(time, formatter);
    Time sqlTime = Time.valueOf(localTime);

    // Konversi tanggal ke SQL Date
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(date, formatter);
    Date sqlDate = Date.valueOf(localDate);

    // Konversi file gambar ke byte[]
    byte[] pictBytes = null;

    if (pict != null && !pict.isEmpty()) {
        try {
            pictBytes = pict.getBytes(); // Ambil gambar baru jika ada
        } catch (IOException e) {
            e.printStackTrace();
            // Tangani error sesuai kebutuhan
        }
    } else {
        ActivityMember currAct = this.repoAdd.getById(idActivity);
        pictBytes = currAct.getPicture();
    }

    // Update database
    this.repoAdd.updateActivity(
        idActivity, title, kind, distance, duration, sqlDate, sqlTime, desc, pictBytes);

    return "redirect:/member/activity";
    }

}
