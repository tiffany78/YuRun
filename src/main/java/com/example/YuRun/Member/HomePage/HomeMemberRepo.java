package com.example.YuRun.Member.HomePage;

import java.time.LocalDate;
import java.util.List;

public interface HomeMemberRepo {
    List<Activity> getActivity(String username);
    List<Activity> getActivityAll(String username);
    List<RaceUser> getMyRace(String username);
    List<RaceUser> getUpRace(String username);
    List<Activity> getActivityMonths(LocalDate date, int id_user);
}
