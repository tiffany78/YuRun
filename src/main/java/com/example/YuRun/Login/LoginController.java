package com.example.YuRun.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String login() {
        return "/Login/index";
    }

    public List<LoginUser> findPengguna(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapToPengguna, email);
    }

    private LoginUser mapToPengguna(ResultSet resultSet, int rowNum) throws SQLException {
        return new LoginUser(
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getBoolean("isadmin"),
                resultSet.getBoolean("status")
        );
    }

    @PostMapping
    public String validation(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model, HttpSession session) {

        List<LoginUser> pengguna = findPengguna(email);

        if (pengguna.isEmpty()) {
            model.addAttribute("error", "Email or password is wrong");
            return "Login/index";
        } else {
            String realPassword = pengguna.get(0).getPassword();
            if (realPassword.equals(password)) { // Password benar
                session.setAttribute("username", pengguna.get(0).getName());
                if (pengguna.get(0).isIsadmin()) { // Jika admin
                    return "redirect:/";
                } else { // User biasa
                    return "redirect:/";
                }
            } else { // Password salah
                model.addAttribute("error", "Email or password is wrong");
                return "Login/index";
            }
        }
    }
}