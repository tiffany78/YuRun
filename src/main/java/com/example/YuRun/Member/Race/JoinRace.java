package com.example.YuRun.Member.Race;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinRace {
    private int idRace;
    private int idUser;
    private Time time;
    private String imagePath;
    private boolean status;
}