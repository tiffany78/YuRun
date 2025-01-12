package com.example.YuRun.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.YuRun.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passEncoder;

    @GetMapping("/profile")
    @RequiredRole({ "member"}) // Membatasi akses untuk member dan admin
    public String showProfile(Model model, HttpSession session) {
        try {
            String userEmail = (String) session.getAttribute("email");
            if (userEmail == null) {
                return "redirect:/login"; // Redirect ke login jika belum login
            }

            // Cari pengguna berdasarkan email
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                model.addAttribute("error", "User not found");
                return "error";
            }

            // Tambahkan user ke model untuk akses di view
            model.addAttribute("user", user);
            // Arahkan ke HTML berdasarkan role pengguna

            return "Profile/index"; // View untuk member
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load profile: " + e.getMessage());
            return "error"; // Halaman error jika terjadi masalah
        }
    }

    @GetMapping("/profileAdmin")
    @RequiredRole({ "admin" }) // Membatasi akses untuk member
    public String showProfileAdmin(Model model, HttpSession session) {
        try {
            String userEmail = (String) session.getAttribute("email");
            if (userEmail == null) {
                return "redirect:/login"; // Redirect ke login jika belum login
            }

            // Cari pengguna berdasarkan email
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                model.addAttribute("error", "User not found");
                return "error";
            }

            // Tambahkan user ke model untuk akses di view
            model.addAttribute("user", user);
            // Arahkan ke HTML berdasarkan role pengguna

            return "Profile/indexAdmin"; // View untuk admin

        } catch (Exception e) {
            model.addAttribute("error", "Failed to load profile: " + e.getMessage());
            return "error"; // Halaman error jika terjadi masalah
        }
    }

    // Member
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
            if (!passEncoder.matches(password, currentUser.getPassword())) {
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
            if (!passEncoder.matches(password, currentUser.getPassword())) {
                model.addAttribute("error2", "Incorrect password");
                model.addAttribute("user", currentUser);
                return "Profile/index";
            }
    
            userRepository.updateEmail(userEmail, newEmail); // Metode ini sudah menangani pengecekan password di repository
    
            // Update session email
            session.setAttribute("email", newEmail);
    
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
            if (!passEncoder.matches(oldPassword, currentUser.getPassword())) {
                model.addAttribute("error3", "Incorrect old password");
                model.addAttribute("user", currentUser);
                return "Profile/index";
            }

            userRepository.updatePassword(userEmail, newPassword); // Metode ini sudah menangani pengecekan password di
                                                                   // repository
            return "redirect:/profile";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update password: " + e.getMessage());
            return "error";
        }
    }

    // Admin
    @PostMapping("/profileAdmin/updateName")
    public String updateNameAdmin(@RequestParam("newName") String newName, @RequestParam("password") String password,
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
            if (!passEncoder.matches(password, currentUser.getPassword())) {
                model.addAttribute("error1", "Incorrect password");
                model.addAttribute("user", currentUser);
                return "Profile/indexAdmin";
            }

            userRepository.updateName(userEmail, newName);
            return "redirect:/profileAdmin";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update name: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/profileAdmin/updateEmail")
    public String updateEmailAdmin(@RequestParam("newEmail") String newEmail, @RequestParam("password") String password,
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
            if (!passEncoder.matches(password, currentUser.getPassword())) {
                model.addAttribute("error2", "Incorrect password");
                model.addAttribute("user", currentUser);
                return "Profile/indexAdmin";
            }

            userRepository.updateEmail(userEmail, newEmail); // Metode ini sudah menangani pengecekan password di
                                                             // repository
            session.setAttribute("email", newEmail);
            
            return "redirect:/profileAdmin";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update email: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/profileAdmin/updatePassword")
    public String updatePasswordAdmin(@RequestParam("newPassword") String newPassword,
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
            if (!passEncoder.matches(oldPassword, currentUser.getPassword())) {
                model.addAttribute("error3", "Incorrect old password");
                model.addAttribute("user", currentUser);
                return "Profile/indexAdmin";
            }

            userRepository.updatePassword(userEmail, newPassword); // Metode ini sudah menangani pengecekan password di
                                                                   // repository
            return "redirect:/profileAdmin";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update password: " + e.getMessage());
            return "error";
        }
    }
}
