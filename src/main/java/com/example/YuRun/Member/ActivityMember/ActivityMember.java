package com.example.YuRun.Member.ActivityMember;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityMember {
    private int id_activity;
    private int id_user;
    private String title;
    private String kind;
    private Double distance;
    private String duration;
    private Date date;
    private Time time;
    private String description;
    private byte[] picture;
    private String path_pict;
}
