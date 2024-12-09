package com.example.YuRun.Admin.RaceService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.YuRun.Admin.Homepage.Race;

@Repository
public class JDBCRace implements RaceRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Race> findRace() {
        String sql = "SELECT * FROM race ORDER BY start_date";
        return jdbcTemplate.query(sql, this::mapRowToRace);
    } 

    private Race mapRowToRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getTime("time"),
            resultSet.getDouble("distance")
        );
    }
}
