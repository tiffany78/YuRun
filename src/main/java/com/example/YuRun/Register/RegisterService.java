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

    public boolean register(RegisterUser user) throws Exception {
        // Enkripsi password sebelum disimpan
        user.setPassword(passEncoder.encode(user.getPassword()));
        
        if (!isUserValid(user)) {
            return false;
        }

        repo.tambahUser(user);
        return true;
    }

    private boolean isUserValid(RegisterUser user) throws Exception {
        // Periksa nama dan email secara bersamaan
        Optional<RegisterUser> existingUserByEmail = repo.findByEmail(user.getEmail());
        Optional<RegisterUser> existingUserByName = repo.findByUsername(user.getName());

        return existingUserByEmail.isEmpty() && existingUserByName.isEmpty();
    }

    public String getValidationError(RegisterUser user) throws Exception {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            return "email";
        }
        if (repo.findByUsername(user.getName()).isPresent()) {
            return "name";
        }
        return null;
    }
}