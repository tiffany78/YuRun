package com.example.YuRun.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.YuRun.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    @RequiredRole({"member", "admin"})
    public String showProfile(Model model, HttpSession session) {
        try {
            String userEmail = (String) session.getAttribute("email");
            if (userEmail == null) {
                return "redirect:/login";
            }

            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                model.addAttribute("error", "User not found");
                return "error";
            }

            model.addAttribute("user", user);
            return "Profile/index";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load profile: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/profile/updateName")
    public String updateName(@RequestParam("newName") String newName, @RequestParam("password") String password,
            HttpSession session, Model model) {
        try {
            String userEmail = (String) session.getAttribute("email");
            if (userEmail == null) {
                return "redirect:/login";
            }

            User currentUser = userRepository.findByEmail(userEmail);
            if (currentUser == null) {
                model.addAttribute("error", "User not found");
                return "error";
            }

            // Pengecekan password
            if (!currentUser.getPassword().equals(password)) {
                model.addAttribute("error1", "Incorrect password");
                model.addAttribute("user", currentUser); 
                return "Profile/index";
            }

            userRepository.updateName(userEmail, newName); 
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update name: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/profile/updateEmail")
    public String updateEmail(@RequestParam("newEmail") String newEmail, @RequestParam("password") String password,
            HttpSession session, Model model) {
        try {
            String userEmail = (String) session.getAttribute("email");
            if (userEmail == null) {
                return "redirect:/login";
            }

            User currentUser = userRepository.findByEmail(userEmail);
            if (currentUser == null) {
                model.addAttribute("error", "User not found");
                return "error";
            }

            // Pengecekan password
            if (!currentUser.getPassword().equals(password)) {
                model.addAttribute("error2", "Incorrect password");
                model.addAttribute("user", currentUser); 
                return "Profile/index";
            }

            userRepository.updateEmail(userEmail, newEmail); // Metode ini sudah menangani pengecekan password di repository
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update email: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/profile/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String newPassword,
            @RequestParam("oldPassword") String oldPassword, HttpSession session, Model model) {
        try {
            String userEmail = (String) session.getAttribute("email");
            if (userEmail == null) {
                return "redirect:/login";
            }

            User currentUser = userRepository.findByEmail(userEmail);
            if (currentUser == null) {
                model.addAttribute("error", "User not found");
                return "error";
            }

            // Pengecekan password
            if (!currentUser.getPassword().equals(oldPassword)) {
                model.addAttribute("error3", "Incorrect old password");
                model.addAttribute("user", currentUser); 
                return "Profile/index";
            }

            userRepository.updatePassword(userEmail, newPassword); // Metode ini sudah menangani pengecekan password di repository
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update password: " + e.getMessage());
            return "error";
        }
    }
}
