package com.example.YuRun.Login;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return "/Login/index";
    }

    @GetMapping("/error404")
    public String error() {
        return "/ErrorLogin/errorPage";
    }

    @GetMapping("/errorWrong")
    public String error2(){
        return "/ErrorLogin/errorPageWrongRole";
    }

    @PostMapping("/login")
    public String validation(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model, HttpSession session) {

        LoginUser pengguna =loginService.login(email, password,model);
        if(pengguna != null){
           if(pengguna.getIsadmin()==1){
                session.setAttribute("email", pengguna.getEmail());
                session.setAttribute("username", pengguna.getName());
                session.setAttribute("id_user", pengguna.getId_user());
                session.setAttribute("peran", "admin");
                return "redirect:/admin/home";
           }
           else{
                session.setAttribute("email", pengguna.getEmail());
                session.setAttribute("id_user", pengguna.getId_user());
                session.setAttribute("peran", "member");
                session.setAttribute("username", pengguna.getName());
                return "redirect:/member";
           }
        }

        model.addAttribute("status", "failed");
        return "Login/index";
    }

    @GetMapping("/directHome")
    public String goHome (HttpSession session){
        String peran = (String) session.getAttribute("peran");
        if(peran.equals("admin")){
            return "redirect:/admin/home";
        }
        else{
            return "redirect:/member";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}