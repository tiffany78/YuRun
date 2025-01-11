package com.example.YuRun.Member.Race;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private int id_race; 
    private String title;       
    private Date end_date; 
    private Double distance;
    private String description;
    private boolean status;
    private boolean iswinner;
    private LocalDateTime startDateTime;
}