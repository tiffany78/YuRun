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
    public String index(@RequestParam(value = "filter", required = false, defaultValue = "") String filter, Model model) {
        List<User> userList = userInformationRepository.findAll(filter);
        model.addAttribute("userList", userList);
        model.addAttribute("filter", filter);
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