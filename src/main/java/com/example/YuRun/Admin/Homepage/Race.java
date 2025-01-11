package com.example.YuRun.Admin.Homepage;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Race {
    private int idRace; 
    private String title;       
    private Date end_date; 
    private Double distance;
    private String description;
    private LocalDateTime startDateTime;
}
