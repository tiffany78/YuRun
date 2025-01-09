package com.example.YuRun.Member.Race;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRaceRepository {
    List<Race> findAllRaces();
    void exitRace(int id_race, int id_user);
    boolean addJoinRace(int id_race, int id_user);
    boolean checkJoinStatus(int id_race, int id_user);
    Race findRaceById(int idRace);
    void addRaceActivity(int idRace, int idUser, String duration, String pathPict);
    void updateRaceStatus(int idRace, int idUser);
    List<Race> findAvailableRacesForUser(int idUser, String filter, String sort);
    List<RaceActivity> getRaceActivities(int id_user, String filter, String sort, int entries, int offset);
    int getTotalRaceActivities(int id_user, String filter);
}