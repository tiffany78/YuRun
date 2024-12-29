package com.example.YuRun.Admin.RaceService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import com.example.YuRun.Admin.Homepage.Race;

public interface RaceRepository {
    List<CountRace> findRace(String filter, String statusRace);
    void addRace(String title, Time time, Date date, Double distance, String desc);
    Race getById(int id);
    void updateRace(String title, Time time, Date date, Double distance, String desc, int idRace);
    List<ResultRace> getAllResultRace(int id_race, String filter, String statusMember);
    void updateStatus(int idRace, int idUser, boolean status);
    void updateStatusRace(int idRace);
    boolean getRaceStatus(int idRace);
    Map<String, Double> getTitleAndDistance(int idRace);
}
