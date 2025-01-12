package com.example.YuRun.Admin.RaceService;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.YuRun.Admin.Homepage.Race;

@Repository
public class JDBCRace implements RaceRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<CountRace> findRace(String filter, String statusRace, int entries, int offset) {

        // mengubah string jadi boolean 
        boolean statusRaceBool = false;
        if(statusRace.equals("true")){
            statusRaceBool = true;
        }

        String sql = "SELECT * FROM count_race_admin";
        List<Object> filterList = new ArrayList<>();
    
        // Menambahkan filter berdasarkan 'filter' (title)
        if (filter != null && !filter.isEmpty()) {
            sql += " WHERE title ILIKE ?";
            filterList.add("%" + filter + "%");
        }
    
        // Menambahkan filter berdasarkan statusRace jika diberikan
        if (statusRace != null && !statusRace.equals("null")) {
            // Menggunakan 'status' untuk memfilter berdasarkan nilai status yang dipilih
            if (!filterList.isEmpty()) {
                sql += " AND status = ?";
            } else {
                sql += " WHERE status = ?";
            }
            filterList.add(statusRaceBool);
        }
    
        sql += " ORDER BY status ASC, start_date";

        if(entries > 0){
            sql += " LIMIT ? OFFSET ?";
            filterList.add(entries);
            filterList.add(offset);
        }
    
        return jdbcTemplate.query(sql, this::mapRowToCountrace, filterList.toArray());
    }
    
    public int getTotalEntries(String filter, String statusRace) {
        // mengubah string jadi boolean 
        boolean statusRaceBool = false;
        if(statusRace.equals("true")){
            statusRaceBool = true;
        }

        String sql = "SELECT * FROM count_race_admin";
        List<Object> filterList = new ArrayList<>();
    
        // Menambahkan filter berdasarkan 'filter' (title)
        if (filter != null && !filter.isEmpty()) {
            sql += " WHERE title ILIKE ?";
            filterList.add("%" + filter + "%");
        }
    
        // Menambahkan filter berdasarkan statusRace jika diberikan
        if (statusRace != null && !statusRace.equals("null")) {
            // Menggunakan 'status' untuk memfilter berdasarkan nilai status yang dipilih
            if (!filterList.isEmpty()) {
                sql += " AND status = ?";
            } else {
                sql += " WHERE status = ?";
            }
            filterList.add(statusRaceBool);
        }
    
        sql += " ORDER BY status ASC, start_date";

        List<CountRace> list = jdbcTemplate.query(sql, this::mapRowToCountrace, filterList.toArray());
        return list.size();
    }

    private CountRace mapRowToCountrace(ResultSet resultSet, int rowNum) throws SQLException {
        LocalDateTime startDateTime = resultSet.getDate("start_date").toLocalDate().atStartOfDay();

        return new CountRace(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"), 
            resultSet.getInt("count"),
            resultSet.getBoolean("status"),
            startDateTime
        );
    }

    private Race mapRowToRace(ResultSet resultSet, int rowNum) throws SQLException {
        LocalDateTime startDateTime = resultSet.getDate("start_date").toLocalDate().atStartOfDay();

        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"), 
            startDateTime
        );
    }

    public void addRace(String title, Date date, Double distance, String desc) {
        String sql = "INSERT INTO public.race(title, start_date, distance, description, status) VALUES (?, ?, ?, ?, FALSE)";
        jdbcTemplate.update(sql, title, date, distance, desc);
    }

    public Race getById(int id){
        String sql = "SELECT * FROM race WHERE id_race = ?";
        List<Race> list = jdbcTemplate.query(sql, this::mapRowToRace, id);
        return list.get(0);
    }

    public void updateRace(String title, Date date, Double distance, String desc, int idRace){
        String sql = "UPDATE race SET distance = ?, start_date = ?, title = ?, description = ? WHERE id_race = ?";

        jdbcTemplate.update(sql, distance, date, title, desc, idRace);
    }

    public Map<String, Double> getTitleAndDistance(int idRace) {
        String sql = "SELECT title, distance FROM race WHERE id_race = ?";
        
        return jdbcTemplate.query(sql, rs -> {
            Map<String, Double> resultMap = new HashMap<>();
            while (rs.next()) {
                resultMap.put(rs.getString("title"), rs.getDouble("distance"));
            }
            return resultMap;
        }, idRace);
    }

    public List<ResultRace> getAllResultRace(int id_race, String filter, String statusMember){
        String sql = "SELECT * FROM show_race_admin WHERE id_race = ?";

        List<Object> params = new ArrayList<>(); 
        params.add(id_race);

        if (filter != null && !filter.isEmpty()) {
            sql += " AND name ILIKE ?";
            params.add("%" + filter + "%");
        }

        if (statusMember != null && statusMember.length() > 0) {
            sql += " AND status = ?";
            boolean status = true;
            if(statusMember.equals("false")){
                status = false;
            }
            params.add(status);
        }
        sql += " ORDER BY member_duration";

        return jdbcTemplate.query(sql, this::mapRowToResultRace, params.toArray());
    }

    public void updateStatus(int idRace, int idUser, boolean status) {
        String sql = "UPDATE joinrace SET status = ? WHERE id_race = ? AND id_user = ?";
        jdbcTemplate.update(sql, status, idRace, idUser);
    } 
    
    public void updateStatusRace(int idRace) {
        String sql = "UPDATE race SET status = TRUE WHERE id_race = ?";
        jdbcTemplate.update(sql, idRace);
    }

    private ResultRace mapRowToResultRace(ResultSet resultSet, int rowNum) throws SQLException {
        Boolean status = (Boolean) resultSet.getObject("status");

        return new ResultRace(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDouble("distance"),
            resultSet.getInt("id_user"),
            resultSet.getString("name"),
            resultSet.getString("member_duration"), 
            resultSet.getString("path_pict"),
            status
        );
    }

    public boolean getRaceStatus(int idRace) {
        String sql = "SELECT status FROM race WHERE id_race = ?";
        try {
            Boolean status = jdbcTemplate.queryForObject(sql, boolean.class, idRace);

            if(status == null || status == false){
                return false;
            }
            else {
                return true;
            }
        } catch (EmptyResultDataAccessException e) {
            // Jika tidak ada data ditemukan
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving race status");
        }
    }

    public void deleteRace(int id_race){
        String sql = "DELETE FROM joinrace WHERE id_race = ?";
        jdbcTemplate.update(sql, id_race);

        sql = "DELETE FROM race WHERE id_race = ?";
        jdbcTemplate.update(sql, id_race);
    }

    public String getWinner(int id_race) {
        String sql = "SELECT users.name FROM joinrace " +
                     "JOIN users ON users.id_user = joinrace.id_user " +
                     "WHERE joinrace.id_race = ? AND joinrace.isWinner = TRUE";
    
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id_race}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "No winner found for this race";
        }
    }    

    public void setWinner(int id_race) {
        String selectSql = "SELECT * FROM joinrace WHERE id_race = ? AND status = TRUE ORDER BY duration ASC LIMIT 1";
        String updateSql = "UPDATE joinrace SET isWinner = TRUE WHERE id_race = ? AND id_user = ?";
        List<JoinRaceService> results = jdbcTemplate.query(selectSql, this::mapRowToJoinRace, id_race);

        if (!results.isEmpty()) {
            JoinRaceService winner = results.get(0);
            jdbcTemplate.update(updateSql, id_race, winner.getIdUser());
            System.out.println("Winner set for raceId " + id_race + ", userId " + winner.getIdUser());
        } else {
            System.out.println("No participants found for raceId " + id_race);
        }
    }

    // Method untuk memetakan hasil query ke objek JoinRace
    private JoinRaceService mapRowToJoinRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new JoinRaceService(
            resultSet.getInt("id_race"), 
            resultSet.getInt("id_user"), 
            resultSet.getString("duration"), 
            resultSet.getString("path_pict"), 
            resultSet.getBoolean("status"), 
            resultSet.getBoolean("isWinner"));
    }
}
