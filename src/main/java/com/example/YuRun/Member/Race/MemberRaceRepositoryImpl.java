package com.example.YuRun.Member.Race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRaceRepositoryImpl implements MemberRaceRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        LocalDateTime startDateTime = resultSet.getDate("end_date").toLocalDate().atStartOfDay();

        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("end_date"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"),
            resultSet.getBoolean("status"),
            resultSet.getBoolean("iswinner"),
            startDateTime
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
    public boolean checkUploadRace(int id_race, int id_user) {
        String sql = "SELECT duration FROM joinrace WHERE id_race = ? AND id_user = ?";
        Boolean result = jdbcTemplate.query(sql, 
            rs -> rs.next() ? rs.getString("duration") != null : false,
            id_race, id_user);
        return result;
    }       

    @Override
    public Race findRaceById(int idRace) {
        String sql = "SELECT id_race, title, end_date, distance, description, status FROM race WHERE id_race = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToRaceMember, idRace);
    }

    private Race mapRowToRaceMember(ResultSet resultSet, int rowNum) throws SQLException {
        LocalDateTime startDateTime = resultSet.getDate("end_date").toLocalDate().atStartOfDay();

        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("end_date"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"),
            resultSet.getBoolean("status"),
            resultSet.getBoolean("status"),
            startDateTime
        );
    }

    @Override
    public void addRaceActivity(int idRace, int idUser, String duration, String pathPict) {
        String sql = "UPDATE joinrace SET duration = ?, path_pict = ? WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, duration, pathPict, idRace, idUser);
    }

    @Override
    public void updateRaceStatus(int idRace, int idUser) {
        String sql = "UPDATE joinrace SET status = true WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, idRace, idUser);
    }

    @Override
    public List<Race> findAvailableRacesForUser(int idUser, String filter, String sort, String status) {
        StringBuilder sql = new StringBuilder(
            "SELECT r.*, COALESCE(jr.iswinner, FALSE) AS iswinner " +
            "FROM race r " +
            "LEFT JOIN joinrace jr ON r.id_race = jr.id_race AND jr.id_user = ? " +
            "WHERE (jr.id_race IS NULL OR (jr.id_user = ? AND jr.status = true))"
        );
        
        List<Object> params = new ArrayList<>();
        params.add(idUser);
        params.add(idUser);

        if (filter != null && !filter.isEmpty()) {
            sql.append("AND LOWER(r.title) LIKE LOWER(?) ");
            params.add("%" + filter + "%");
        }

        LocalDate currDate = LocalDate.now(); 
        if (status != null && !status.isEmpty()) {
            if (status.equals("Open")) {
                sql.append("AND end_date >= ? ");
            } 
            else {
                sql.append("AND end_date < ? ");
            }
            params.add(currDate);
        }

        // Add sorting
        if (sort != null && !sort.equals("null")) {
            switch (sort) {
                case "Distance-Asc":
                    sql.append("ORDER BY r.distance ASC ");
                    break;
                case "Distance-Desc":
                    sql.append("ORDER BY r.distance DESC ");
                    break;
                case "Date-Asc":
                    sql.append("ORDER BY r.end_date ASC ");
                    break;
                case "Date-Desc":
                    sql.append("ORDER BY r.end_date DESC ");
                    break;
                default:
                    sql.append("ORDER BY r.end_date DESC "); // Default sort
            }
        } else {
            sql.append("ORDER BY r.end_date DESC "); // Prioritize isWinner
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), this::mapRowToRace);
    }

    @Override
    public List<RaceActivity> getRaceActivities(int id_user, String filter, String sort, String status) {
        StringBuilder sql = new StringBuilder(
            "SELECT r.id_race, r.title, jr.duration, jr.path_pict, jr.status as statusMember, r.end_date, r.distance, r.description, r.status as statusRace " +
            "FROM race r " +
            "JOIN joinrace jr ON r.id_race = jr.id_race " +
            "WHERE jr.id_user = ? "
        );
        
        List<Object> params = new ArrayList<>();
        params.add(id_user);

        if (filter != null && !filter.isEmpty()) {
            sql.append("AND LOWER(r.title) LIKE LOWER(?) ");
            params.add("%" + filter + "%");
        }

        if (status != null && !status.isEmpty()) {
            if(status.equals("Status-True")){
                sql.append("AND r.status = TRUE ");
            }
            else{
                sql.append("AND r.status = FALSE ");
            }
        }

        if (sort != null) {
            switch (sort) {
                case "Distance-Asc":
                    sql.append("ORDER BY r.distance ASC ");
                    break;
                case "Distance-Desc":
                    sql.append("ORDER BY r.distance DESC ");
                    break;
                case "Duration-Asc":
                    sql.append("ORDER BY jr.duration ASC ");
                    break;
                case "Duration-Desc":
                    sql.append("ORDER BY jr.duration DESC ");
                    break;
                case "Date-Asc":
                    sql.append("ORDER BY r.end_date ASC ");
                    break;
                case "Date-Desc":
                    sql.append("ORDER BY r.end_date DESC ");
                    break;
                default:
                    sql.append("ORDER BY r.end_date DESC ");
            }
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new RaceActivity(
            rs.getInt("id_race"),
            rs.getString("title"),
            rs.getString("duration"),
            rs.getString("path_pict"),
            rs.getBoolean("statusmember"),
            rs.getDate("end_date"),
            rs.getDouble("distance"),
            rs.getString("description"),
            rs.getBoolean("statusrace")
        ));
    }

    @Override
    public int getTotalRaceActivities(int id_user, String filter) {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM race r " +
            "JOIN joinrace jr ON r.id_race = jr.id_race " +
            "WHERE jr.id_user = ? AND jr.status = false "
        );
        
        List<Object> params = new ArrayList<>();
        params.add(id_user);

        if (filter != null && !filter.isEmpty()) {
            sql.append("AND LOWER(r.title) LIKE LOWER(?) ");
            params.add("%" + filter + "%");
        }

        return jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
    }
}