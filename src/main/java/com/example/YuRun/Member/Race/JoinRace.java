package com.example.YuRun.Member.Race;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinRace {
    private int id_race;
    private int id_user;
    private String duration;
    private String path_pict;
    private boolean status;
}

// @Data
// @AllArgsConstructor
// public class JoinRace {
//     private int idRace;
//     private int idUser;
//     private Time time;
//     private String imagePath;
//     private boolean status;
// }