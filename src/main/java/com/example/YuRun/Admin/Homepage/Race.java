package com.example.YuRun.Admin.Homepage;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private int idRace; 
    private String title;       
    private Date start_date; 
    private Time time;  
    private Double distance;
    private String description;
}
