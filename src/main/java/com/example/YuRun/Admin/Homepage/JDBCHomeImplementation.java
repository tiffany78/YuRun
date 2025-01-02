package com.example.YuRun.Admin.Homepage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCHomeImplementation implements HomeRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Race> findRace() {
        LocalDate currDate = LocalDate.now();
        String sql = "SELECT * FROM race WHERE start_date > ? ORDER BY start_date LIMIT 3";
        return jdbcTemplate.query(sql, this::mapRowToRaceHome, currDate);
    } 

    public List<Race> getRaceLandingPage() {
        String sql = "SELECT * FROM race ORDER BY start_date DESC LIMIT 3";
        return jdbcTemplate.query(sql, this::mapRowToRaceHome);
    }

    private Race mapRowToRaceHome(ResultSet resultSet, int rowNum) throws SQLException {
        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getTime("time"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"),
            null
        );
    }

    public List<JoinRace> countRace(){
        String sql = "SELECT title, count FROM count_race_admin ORDER BY start_date";
        return jdbcTemplate.query(sql, this::mapRowToRace);
    }

    private JoinRace mapRowToRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new JoinRace(
            resultSet.getString("title"),
            resultSet.getInt("count")
        );
    }
}

