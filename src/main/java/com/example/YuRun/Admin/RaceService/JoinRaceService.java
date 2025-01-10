package com.example.YuRun.Admin.RaceService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinRaceService {
    private int idRace;
    private int idUser;
    private String duration;
    private String pathPict;
    private boolean status;
    private boolean isWinner;
}
