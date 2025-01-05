package com.example.YuRun.Member.Race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRaceRepositoryImpl implements MemberRaceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Race> findAllRaces() {
        String sql = "SELECT id_race, title, start_date, time, distance FROM race";
        return jdbcTemplate.query(sql, this::mapRowToRace);
    }

    @Override
    public boolean addJoinRace(int idRace, int idUser) {
        try {
            // First check if entry exists
            String checkSql = "SELECT status FROM joinrace WHERE id_race = ? AND id_user = ?";
            List<Boolean> existing = jdbcTemplate.query(checkSql, 
                (rs, rowNum) -> rs.getBoolean("status"), 
                idRace, idUser);
    
            if (!existing.isEmpty()) {
                // Update existing entry
                String updateSql = "UPDATE joinrace SET status = TRUE, time = CURRENT_TIME WHERE id_race = ? AND id_user = ?";
                int rowsAffected = jdbcTemplate.update(updateSql, idRace, idUser);
                return rowsAffected > 0;
            } else {
                // Create new entry
                String insertSql = "INSERT INTO joinrace (id_race, id_user, time, status) VALUES (?, ?, CURRENT_TIME, TRUE)";
                int rowsAffected = jdbcTemplate.update(insertSql, idRace, idUser);
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void exitRace(int idRace, int idUser) {
        String sql = "UPDATE joinrace SET status = false WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, idRace, idUser);
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

    @Override
    public boolean checkJoinStatus(int idRace, int idUser) {
        String sql = "SELECT status FROM joinrace WHERE id_race = ? AND id_user = ?";
        List<Boolean> results = jdbcTemplate.query(sql, 
            (rs, rowNum) -> rs.getBoolean("status"), 
            idRace, idUser);
        return !results.isEmpty() && results.get(0);
    }
}