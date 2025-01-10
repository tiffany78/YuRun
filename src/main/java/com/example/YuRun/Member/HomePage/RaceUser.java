package com.example.YuRun.Member.HomePage;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaceUser {
    private int idRace; 
    private String title;       
    private Date start_date; 
    private Double distance;
    private String description;
}
