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

// @Controller
// public class LoginController {

//     @GetMapping("/login")
//     public String index2(){
//         return "/Login/index";
//     }
// }

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String login(){
        return "/Login/index";
    }

    public List<LoginUser> findPengguna(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapToPengguna, email);
    }    

    private LoginUser mapToPengguna(ResultSet resultSet, int rowNum) throws SQLException{
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
        @RequestParam("name") String email,
        @RequestParam("password") String password,
        Model model, HttpSession session) {

        List<LoginUser> pengguna = findPengguna(email);

        if (pengguna.isEmpty()) {
            return "Login/index";
        } else {
            String realPassword = pengguna.get(0).getPassword();
            if (realPassword.equals(password)) {    //Password benar
                // Simpan nama pengguna dalam session
                session.setAttribute("username", pengguna.get(0).getName());
                // Jika yang login adalah admin
                // Cek bit isAdmin dari database
                int isAdmin = pengguna.get(0).getIsAdmin(); // Asumsi ada method getIsAdmin() yang mengembalikan nilai bit (1 atau 0)

                if (isAdmin == 1) {  // Jika 1 berarti admin
                    return "redirect:/admin/LandingPage";
                } else {  // Jika 0 berarti user biasa
                    return "redirect:/user";
                }   
            } 
            else {  //Password salah
                return "LoginUser/index";
            }
        }
    }
}