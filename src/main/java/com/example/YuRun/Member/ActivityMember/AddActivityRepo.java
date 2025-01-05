package com.example.YuRun.Member.ActivityMember;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface AddActivityRepo {
    void addActivity(int id_user, String title, String kind, Double distance, String duration, Date date, Time time, String description, String path);
    
    ActivityMember getById(int id_activity);

    void updateActivity(int id_activity, String title, String kind, Double distance, String duration, Date date, Time time, String description, String path_pict);

    List<ActivityMember> getAllActivityMember (int id_user, String filter, String kind, int entries, int offset, String sort);

    void deleteActivity(int id_activity);

    int getTotalEntries(int id_user, String filter, String kind);
}
