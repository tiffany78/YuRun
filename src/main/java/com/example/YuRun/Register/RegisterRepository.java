package com.example.YuRun.Register;
import java.util.Optional;

public interface RegisterRepository {
    void tambahUser(RegisterUser user);
    Optional<RegisterUser> findByUsername(String username);
} 