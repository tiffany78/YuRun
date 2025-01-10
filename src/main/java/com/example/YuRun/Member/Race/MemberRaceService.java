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

    public boolean joinRace(int id_race, int id_user) {
        return raceRepository.addJoinRace(id_race, id_user);
    }

    public void exitRace(int id_race, int id_user) {
        raceRepository.exitRace(id_race, id_user);
    }

    public boolean isUserJoinedRace(int id_race, int id_user) {
        return raceRepository.checkJoinStatus(id_race, id_user);
    }

    public Race getRaceById(int idRace) {
        return raceRepository.findRaceById(idRace);
    }

    public void saveRaceActivity(int idRace, int idUser, String duration, String pathPict) {
        raceRepository.addRaceActivity(idRace, idUser, duration, pathPict);
        raceRepository.updateRaceStatus(idRace, idUser);
    }
    
    public List<Race> getAvailableRaces(int idUser, String filter, String sort) {
        return raceRepository.findAvailableRacesForUser(idUser, filter, sort);
    }
}