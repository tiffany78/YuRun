package com.example.YuRun.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepo loginRepo;

    public LoginUser login (String email, String password){
        Optional <LoginUser> userOpt = loginRepo.findPengguna(email);

        if(userOpt.isPresent()){
            LoginUser user = userOpt.get();
            if(user.getPassword().equals(password)){
                return user;
            }
        }
        return null;

    }
    
}
