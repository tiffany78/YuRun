package com.example.YuRun.Member.Race;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRaceRepository {
    List<Race> findAllRaces();
    // void joinRace(int idRace, int idUser);
    void exitRace(int idRace, int idUser);
    boolean addJoinRace(int idRace, int idUser);
    boolean checkJoinStatus(int idRace, int idUser);
}