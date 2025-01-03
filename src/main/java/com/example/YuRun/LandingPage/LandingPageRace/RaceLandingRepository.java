package com.example.YuRun.LandingPage.LandingPageRace;

import java.util.List;

import com.example.YuRun.Admin.Homepage.Race;

public interface RaceLandingRepository {
List<Race> findRace(String filter);
}
