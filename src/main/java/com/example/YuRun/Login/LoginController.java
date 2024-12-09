package com.example.YuRun.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController{
    @Autowired
    private LoginRepository repo;

    @GetMapping
    public String login(){
        return "/Login/index";
    }

    /*
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
                // Asumsi ada method getIsAdmin() yang mengembalikan nilai bit (1 atau 0)
                // int isAdmin = pengguna.get(0).getIsAdmin(); 

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
    */
}