package com.example.YuRun.Profile;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findByEmail(String email);

    void updateName(String email, String newName);

    void updateEmail(String email, String newEmail);

    void updatePassword(String email, String newPassword);
}
