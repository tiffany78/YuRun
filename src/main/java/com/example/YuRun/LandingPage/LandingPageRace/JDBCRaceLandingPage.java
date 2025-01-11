package com.example.YuRun.LandingPage.LandingPageRace;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.YuRun.Admin.Homepage.Race;

@Repository
public class JDBCRaceLandingPage implements RaceLandingRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Race> findRace(String filter) {
        LocalDate currDate = LocalDate.now();

        String sql = "SELECT * FROM race WHERE end_date > ?";
        List<Object> fil = new ArrayList<>();
        fil.add(currDate);
    
        // // Menambahkan filter berdasarkan 'filter' (title)
        if (filter != null && !filter.isEmpty()) {
            sql += " AND title ILIKE ?";
            fil.add("%" + filter + "%");
        }

        sql += " ORDER BY end_date";

        return jdbcTemplate.query(sql, this::mapRowToRaceHome, fil.toArray());
    }

    private Race mapRowToRaceHome(ResultSet resultSet, int rowNum) throws SQLException {
        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("end_date"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"),
            null
            );
    }
}
