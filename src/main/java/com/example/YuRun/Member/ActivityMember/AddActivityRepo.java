package com.example.YuRun.Member.ActivityMember;

import java.sql.Date;
import java.sql.Time;

public interface AddActivityRepo {
    void addActivity(int id_user, String title, String kind, Double distance, Date date, Time time, String description, byte[] picture);
    ActivityMember getById(int id_activity);
}