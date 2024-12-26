package com.example.YuRun.Member.Progress;

import java.sql.Date;

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
    private String member_duration;
    private boolean status;
}
