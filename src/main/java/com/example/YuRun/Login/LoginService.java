package com.example.YuRun.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginService {

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private PasswordEncoder passEncoder;

    public LoginUser login (String email, String password, Model model){
        Optional <LoginUser> userOpt = loginRepo.findPengguna(email);
        if(userOpt.isPresent()){
            LoginUser user = userOpt.get();
            // Versi pake encoder
            if(!passEncoder.matches(password, user.getPassword())){//password sesuai
                model.addAttribute("error", "Incorrect password. Please try again.");
                return null;
            }
            else{
                return user;
            }
        }
        else{
            // Pengguna tidak ditemukan
            model.addAttribute("error", "User not found. Please register.");
            return null;
        }  

    }
    
}
