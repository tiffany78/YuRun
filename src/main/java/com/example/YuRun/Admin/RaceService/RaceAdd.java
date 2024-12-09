package com.example.YuRun.Admin.RaceService;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaceAdd {
    private Double distance;
    private LocalDate startDate;
    private LocalTime startTime;
    private String title;
    private String description;
}
