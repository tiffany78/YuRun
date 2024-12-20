package com.example.YuRun.Admin.RaceService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.example.YuRun.Admin.Homepage.Race;

public interface RaceRepository {
    List<Race> findRace();
    void addRace(String title, Time time, Date date, Double distance, String desc);
    Race getById(int id);
    void updateRace(String title, Time time, Date date, Double distance, String desc, int idRace);
}
