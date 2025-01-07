package com.example.YuRun.Member.Race;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRaceRepository {
    List<Race> findAllRaces();
    // void joinRace(int id_race, int id_user);
    void exitRace(int id_race, int id_user);
    boolean addJoinRace(int id_race, int id_user);
    boolean checkJoinStatus(int id_race, int id_user);
}