package com.example.YuRun.Register;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private RegisterRepository repo;

    @Autowired
    private PasswordEncoder passEncoder;

    public boolean register(RegisterUser user){
        //kalo username sudah di list
        Optional<RegisterUser> optiUser = repo.findByUsername(user.getName());
        if(optiUser.isPresent()){
            return false;
        }

        //enkripsi password
        user.setPassword(passEncoder.encode(user.getPassword()));

        try {
            // simpan user ke database
            repo.tambahUser(user);
            return true; // berhasil 
        } catch (Exception e) {
            e.printStackTrace();
            return false; // error 
        }
    }
}
