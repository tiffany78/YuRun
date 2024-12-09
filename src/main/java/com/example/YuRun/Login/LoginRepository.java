package com.example.YuRun.Login;

import java.util.List;

public interface LoginRepository {
    List<LoginUser> findPengguna(String email);
}
