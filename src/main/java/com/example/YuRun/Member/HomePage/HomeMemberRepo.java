package com.example.YuRun.Member.HomePage;

import java.util.List;

public interface HomeMemberRepo {
    List<Activity> getActivity(String username);
}
