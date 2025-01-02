package com.example.YuRun.Admin.UserInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.YuRun.RequiredRole;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserInformationController {
    @Autowired
    private final UserInformationRepository userInformationRepository;

    public UserInformationController(UserInformationRepository userInformationRepository) {
        this.userInformationRepository = userInformationRepository;
    }

    @GetMapping("/userInfo")
    @RequiredRole("admin")
    public String index(
        @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
        @RequestParam(required = false, defaultValue = "null") String statusMember, 
        Model model,
        @RequestParam(value = "entries", required = false, defaultValue = "0") int entries,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page) {

        // Hitung offset berdasarkan halaman dan jumlah entri
        int offset = (page - 1) * entries;

        List<User> userList = userInformationRepository.findAll(filter, statusMember, entries, offset);
        model.addAttribute("userList", userList);
        model.addAttribute("filter", filter);
        model.addAttribute("statusMember", statusMember);

        int totalEntries = userInformationRepository.getTotalEntries(filter, statusMember);
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

        return "/Admin/UserInformation/index";
    }

    @GetMapping("/updateStatus")
    @RequiredRole("admin")
    public String updateStatus(@RequestParam("name") String name, Model model) {
        boolean newStatus = false; // Set to inactive
        userInformationRepository.updateUserStatusByName(name, newStatus);
        return "redirect:/admin/userInfo";
    }
}