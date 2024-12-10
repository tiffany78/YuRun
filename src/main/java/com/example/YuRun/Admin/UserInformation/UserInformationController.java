package com.example.YuRun.Admin.UserInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(@RequestParam(value = "filter", required = false) String filter, Model model) {
        List<User> userList = userInformationRepository.findAll(filter);
        model.addAttribute("userList", userList);
        return "/Admin/UserInformation/index"; // Ganti dengan path sesuai template Anda
    }

    @GetMapping("/updateStatus")
    public String updateStatus(@RequestParam("name") String name, Model model) {
        boolean newStatus = false; // Set to inactive
        userInformationRepository.updateUserStatusByName(name, newStatus);
        return "redirect:/admin/userInfo";
    }
}