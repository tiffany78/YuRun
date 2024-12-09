package com.example.YuRun.Admin.RaceService;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
            resultSet.getDouble("distance"),
            resultSet.getString("description")
        );
    }

    public void addRace(String title, Time time, Date date, Double distance, String desc) {
        String sql = "INSERT INTO public.race(title, time, start_date, distance, description) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
        title, time, date, distance, desc);
    }

    public Race getById(int id){
        String sql = "SELECT * FROM race WHERE id_race = ?";
        List<Race> list = jdbcTemplate.query(sql, this::mapRowToRace, id);
        return list.get(0);
    }

    public void updateRace(String title, Time time, Date date, Double distance, String desc, int idRace){
        String sql = "UPDATE race SET distance = ?, start_date = ?, time = ?, title = ?, description = ? WHERE id_race = ?";

        jdbcTemplate.update(sql, distance, date, time, title, desc, idRace);
    }
}
