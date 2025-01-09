package com.example.YuRun.Member.Race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@Repository
public class MemberRaceRepositoryImpl implements MemberRaceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Race> findAllRaces() {
        String sql = "SELECT id_race, title, start_date, time, distance, description, status FROM race";
        return jdbcTemplate.query(sql, this::mapRowToRace);
    }

    @Override
    public boolean addJoinRace(int id_race, int id_user) {
        try {
            // First check if entry exists
            String checkSql = "SELECT status FROM joinrace WHERE id_race = ? AND id_user = ?";
            List<Boolean> existing = jdbcTemplate.query(checkSql, 
                (rs, rowNum) -> rs.getBoolean("status"), 
                id_race, id_user);
    
            if (!existing.isEmpty()) {
                // Update existing entry
                String updateSql = "UPDATE joinrace SET status = TRUE WHERE id_race = ? AND id_user = ?";
                int rowsAffected = jdbcTemplate.update(updateSql, id_race, id_user);
                return rowsAffected > 0;
            } else {
                // Create new entry
                String insertSql = "INSERT INTO joinrace (id_race, id_user, status) VALUES (?, ?, TRUE)";
                int rowsAffected = jdbcTemplate.update(insertSql, id_race, id_user);
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void exitRace(int id_race, int id_user) {
        String sql = "DELETE FROM joinrace WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, id_race, id_user);
    }

    private Race mapRowToRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getTime("time"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"),
            resultSet.getBoolean("status")
        );
    }

    @Override
    public boolean checkJoinStatus(int id_race, int id_user) {
        String sql = "SELECT status FROM joinrace WHERE id_race = ? AND id_user = ?";
        List<Boolean> results = jdbcTemplate.query(sql, 
            (rs, rowNum) -> rs.getBoolean("status"), 
            id_race, id_user);
        return !results.isEmpty() && results.get(0);
    }

    @Override
    public Race findRaceById(int idRace) {
        String sql = "SELECT id_race, title, start_date, time, distance, description, status FROM race WHERE id_race = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToRace, idRace);
    }

    @Override
    public void addRaceActivity(int idRace, int idUser, String duration, String pathPict) {
        String sql = "UPDATE joinrace SET duration = ?, path_pict = ? WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, duration, pathPict, idRace, idUser);
    }

    @Override
    public void updateRaceStatus(int idRace, int idUser) {
        String sql = "UPDATE joinrace SET status = false WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, idRace, idUser);
    }

    @Override
    public List<Race> findAvailableRacesForUser(int idUser) {
        String sql = """
            SELECT r.* FROM race r 
            LEFT JOIN joinrace jr ON r.id_race = jr.id_race AND jr.id_user = ? 
            WHERE jr.id_race IS NULL 
            OR (jr.id_user = ? AND jr.status = true)
        """;
        return jdbcTemplate.query(sql, this::mapRowToRace, idUser, idUser);
    }
}