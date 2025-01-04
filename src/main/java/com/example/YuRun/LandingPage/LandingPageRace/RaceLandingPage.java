package com.example.YuRun.LandingPage.LandingPageRace;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaceLandingPage {
    private int idRace; 
    private String title;       
    private Date start_date; 
    private Time time;  
    private Double distance;
    private String description;
    private LocalDateTime startDateTime;
}
