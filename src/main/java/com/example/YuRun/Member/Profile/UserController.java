package com.example.YuRun.Member.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.YuRun.RequiredRole;
import com.example.YuRun.Member.ActivityMember.ActivityMember;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/member")
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    @RequiredRole("member")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Ambil data pengguna dari repository
        User user = userRepository.getUserByUsername(username);
        model.addAttribute("user", user);

        return "/Member/Profile/index";
    }

    @GetMapping("/updatePassword")
    @RequiredRole("member")
    public String updatePassword(
            HttpSession session,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Model model) {
        String nameUser = (String) session.getAttribute("name");
        if (nameUser == null) {
            return "redirect:/login";
        }

        try {
            // Panggil metode updatePassword melalui objek userRepository
            userRepository.updatePassword(nameUser, oldPassword, newPassword);
            model.addAttribute("message", "Password berhasil diperbarui.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "/Member/Profile/index";
    }

}