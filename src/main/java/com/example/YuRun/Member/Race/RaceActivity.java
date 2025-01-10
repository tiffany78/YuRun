package com.example.YuRun.Member.Race;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaceActivity {
    private int id_race;
    private String title;
    private String duration;
    private String path_pict;
    private Date start_date;
    private Double distance;
    private String description;
    private boolean status;
}