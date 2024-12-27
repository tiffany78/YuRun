package com.example.YuRun.Member.Progress;

import java.util.List;

import com.example.YuRun.Member.ActivityMember.ActivityMember;

public interface ProgressRepo {
    List<ActivityMember> getAllActivities(int id_user);
    List<ProgressRace> getAllRace(int id_user);
    List<ActivityMember> getWeeklyActivities(int id_user);
    List<ActivityMember> getMonthlyActivities(int id_user);
    List<ActivityMember> getThreeMonthlyActivities(int id_user);
    List<ActivityMember> getYearlyActivities(int id_user);
}
