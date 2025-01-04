package com.example.YuRun.Admin.Homepage;

import java.util.List;

public interface HomeRepository {
    List<Race> findRace();
    List<JoinRace> countRace();
    List<Race> getRaceLandingPage();
}
