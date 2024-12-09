package com.example.YuRun.Admin.RaceService;

import java.util.List;

import com.example.YuRun.Admin.Homepage.Race;

public interface RaceRepository {
    List<Race> findRace();
}
