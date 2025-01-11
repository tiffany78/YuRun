package com.example.YuRun.Member.ActivityMember;

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
    public String home(HttpSession session, Model model,
        @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
        @RequestParam(value = "entries", required = false, defaultValue = "0") int entries,
        @RequestParam(value = "kindActivity", required = false, defaultValue = "null") String kind,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "sort", required = false, defaultValue = "null") String sort) {

        Integer idUserObj = (Integer) session.getAttribute("id_user");
        if (idUserObj == null) {
            return "/ErrorLogin/errorPage";
        }
        int id_user = idUserObj;

        // Hitung offset berdasarkan halaman dan jumlah entri
        int offset = (page - 1) * entries;

        // Dapatkan daftar aktivitas berdasarkan filter dan pagination
        List<ActivityMember> list = this.repoAdd.getAllActivityMember(id_user, filter, kind, entries, offset, sort);

        // Hitung total jumlah entri untuk pagination
        int totalEntries = this.repoAdd.getTotalEntries(id_user, filter, kind);
        if (entries == 0) {
            entries = totalEntries;
        }

        int currEntries = totalEntries;
        if (entries > 0){
            currEntries = Math.min(page * entries, totalEntries);
        }

        // Kirimkan data ke view
        model.addAttribute("activity", list);
        model.addAttribute("filter", filter);
        model.addAttribute("kindActivity", kind);
        model.addAttribute("sort", sort);
        model.addAttribute("entries", entries);
        model.addAttribute("currEntries", currEntries);
        model.addAttribute("totalEntries", totalEntries);
        model.addAttribute("currentPage", page);

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
        @RequestParam("kind") String kind,
        @RequestParam("fileImage") MultipartFile fileImage,
        HttpSession session) throws IOException{

        Integer idUserObj = (Integer) session.getAttribute("id_user");
        if (idUserObj == null) {
            return "/ErrorLogin/errorPage";
        }
        int id_user = idUserObj;
        String duration = String.format("%02d:%02d:%02d", hour, minute, second);
        
        time += ":00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime = LocalTime.parse(time, formatter);
        Time sqlTime = Time.valueOf(localTime);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        Date sqlDate = Date.valueOf(localDate);

        String fileName;
        if (fileImage != null && !fileImage.isEmpty()) {
            // Jika file diunggah
            String timestamp = String.valueOf(System.currentTimeMillis());
            fileName = "idUser_" + id_user + "_" + timestamp + ".jpg";
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
        } else {
            // Jika file tidak diunggah
            fileName = null;
        }

        this.repoAdd.addActivity(id_user, title, kind, distance, duration, sqlDate, sqlTime, desc, fileName);
        return "redirect:/member/activity";
    }

    @GetMapping("/deleteActivity/{idActivity}")
    @RequiredRole("member")
    public String deleteActivity(@PathVariable("idActivity") int idActivity){
        this.repoAdd.deleteActivity(idActivity);
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
    @RequestParam("fileImage") MultipartFile fileImage,
    @PathVariable("idActivity") int idActivity,
    HttpSession session) throws IOException {

    Integer idUserObj = (Integer) session.getAttribute("id_user");
    if (idUserObj == null) {
        return "/ErrorLogin/errorPage";
    }
    int id_user = idUserObj;
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

    // Pergantian path
    String timestamp = String.valueOf(System.currentTimeMillis());
    String fileName = "idUser_" + id_user + "_" + timestamp + ".jpg";
    String uploadDir = "upload/activity-member/";
    Path uploadPath = Paths.get(uploadDir);

    ActivityMember currAct = this.repoAdd.getById(idActivity);
    String oldfileName = currAct.getPath_pict();

    // Buat direktori jika belum ada
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    if (fileImage != null && !fileImage.isEmpty()) {
        // Jika ada file baru, hapus file lama
        if (oldfileName != null && !oldfileName.isEmpty()) {
            Path oldFilePath = uploadPath.resolve(oldfileName);
            if (Files.exists(oldFilePath)) {
                try {
                    Files.delete(oldFilePath); // Hapus file lama
                } catch (IOException e) {
                    throw new IOException("Could not delete old file: " + oldfileName, e);
                }
            }
        }

        // Jika ada file baru, simpan file baru
        fileName = "idUser_" + id_user + "_" + timestamp + ".jpg";

        try (InputStream inputStream = fileImage.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    } else {
        fileName = oldfileName;
    }

    // Update database
    this.repoAdd.updateActivity(
        idActivity, title, kind, distance, duration, sqlDate, sqlTime, desc, fileName);

    return "redirect:/member/activity";
    }

}