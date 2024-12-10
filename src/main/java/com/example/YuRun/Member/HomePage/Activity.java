package com.example.YuRun.Member.HomePage;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Activity {
    private int id_activity;
    private int id_user;
    private String title;
    private String kind;
    private Double distance;
    private String duration;
    private Date date;
    private Time time;
    private String description;
}
