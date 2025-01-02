package com.example.YuRun.Admin.RaceService;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultRace {
    private int id_race;
    private String title;
    private Time race_time;
    private Double distance;
    private int id_user;
    private String name;
    private String member_duration;
    private String path_pict;
    private Boolean status;
}
