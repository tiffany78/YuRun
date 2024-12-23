package com.example.YuRun.Admin.RaceService;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
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
        LocalDateTime startDateTime = resultSet.getDate("start_date").toLocalDate()
            .atTime(resultSet.getTime("time").toLocalTime());
        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getTime("time"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"), 
            startDateTime
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

    public List<ResultRace> getAllResultRace(int id_race){
        String sql = "SELECT * FROM show_race_admin WHERE id_race = ?";
        
        return jdbcTemplate.query(sql, this::mapRowToResultRace, id_race);
    }

    public void updateStatus(int idRace, int idUser, boolean status) {
        String sql = "UPDATE joinrace SET status = ? WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, status, idRace, idUser);
    }    

    private ResultRace mapRowToResultRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new ResultRace(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getTime("race_time"),
            resultSet.getDouble("distance"),
            resultSet.getInt("id_user"),
            resultSet.getString("name"),
            resultSet.getTime("member_time"), 
            resultSet.getString("path_pict"),
            resultSet.getBoolean("status")
        );
    }
}
