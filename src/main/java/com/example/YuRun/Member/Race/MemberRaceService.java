package com.example.YuRun.Member.Race;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MemberRaceService {
    private MemberRaceRepository raceRepository;

    public MemberRaceService(MemberRaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public List<Race> getAllRaces() {
        return raceRepository.findAllRaces();
    }

    public boolean joinRace(int idRace, int idUser) {
        try {
            return raceRepository.addJoinRace(idRace, idUser);
        } catch (Exception e) {
            // Log error untuk debugging
            System.err.println("Error joining race: " + e.getMessage());
            return false;
        }
    }

    public void exitRace(int idRace, int idUser) {
        raceRepository.exitRace(idRace, idUser);
    }

    public boolean isUserJoinedRace(int idRace, int idUser) {
        return raceRepository.checkJoinStatus(idRace, idUser);
    }
}