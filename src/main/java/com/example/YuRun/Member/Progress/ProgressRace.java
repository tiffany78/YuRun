package com.example.YuRun.Member.Progress;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressRace {
    private int id_race;
    private String title;
    private Date race_date;
    private Double distance;
    private int id_user;
    private String name;
    private Time member_time;
    private boolean status;
}
