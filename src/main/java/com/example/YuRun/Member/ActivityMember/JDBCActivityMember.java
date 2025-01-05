package com.example.YuRun.Member.ActivityMember;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCActivityMember implements AddActivityRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addActivity(int id_user, String title, String kind, Double distance, String duration, Date date, Time time, String description, String path){
        String sql = "INSERT INTO Activity(id_user, title, kind, distance, duration, date, time, description, path_pict) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
        id_user, title, kind, distance, duration, date, time, description, path);
    }

    public void deleteActivity(int id_activity){
        String sql = "DELETE FROM Activity WHERE id_activity = ?";
        jdbcTemplate.update(sql, id_activity);
    }

    public List<ActivityMember> getAllActivityMember (int id_user, String filter, String kind, int entries, int offset, String sort){
        List<Object> params = new ArrayList<>();
        String sql = "SELECT * FROM Activity WHERE id_user = ?";
        params.add(id_user);

        if (filter != null && !filter.isEmpty()) {
            sql += " AND title ILIKE ?";
            params.add("%" + filter + "%");
        }

        if(kind != null && !kind.equals("null")){
            sql += " AND kind = ?";
            params.add(kind);
        }

        if(sort != null && !sort.equals("null")){
            switch (sort) {
                case "Distance-Asc":
                    sql += " ORDER BY distance";
                    break;
                case "Distance-Desc":
                    sql += " ORDER BY distance DESC";
                    break;
                case "Duration-Asc":
                    sql += " ORDER BY duration";
                    break;
                case "Duration-Desc":
                    sql += " ORDER BY duration DESC";
                    break;
                case "Date-Asc":
                    sql += " ORDER BY date";
                    break;
                case "Date-Desc":
                    sql += " ORDER BY date DESC";
                    break;
                default:
                    sql += " ORDER BY date DESC";
                    break;
            }
        }

        if(entries > 0){
            sql += " LIMIT ? OFFSET ?";
            params.add(entries);
            params.add(offset);
        }

        return jdbcTemplate.query(sql, this::maptoRowActivityMember, params.toArray());
    }

    public int getTotalEntries(int id_user, String filter, String kind) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT * FROM Activity WHERE id_user = ?";
        params.add(id_user);

        if (filter != null && !filter.isEmpty()) {
            sql += " AND title ILIKE ?";
            params.add("%" + filter + "%");
        }

        if (kind != null && !kind.equals("null")) {
            sql += " AND kind = ?";
            params.add(kind);
        }

        List<ActivityMember> list = jdbcTemplate.query(sql, this::maptoRowActivityMember, params.toArray());
        return list.size();
    }          

    public ActivityMember getById(int id_activity){
        String sql = "SELECT * from activity WHERE id_activity = ?";
        List<ActivityMember> list = jdbcTemplate.query(sql, this::maptoRowActivityMember,id_activity);
        return list.get(0);
    }

    private ActivityMember maptoRowActivityMember(ResultSet resultSet, int rowNum) throws SQLException {
        return new ActivityMember(
            resultSet.getInt("id_activity"), 
            resultSet.getInt("id_user"), 
            resultSet.getString("title"), 
            resultSet.getString("kind"), 
            resultSet.getDouble("distance"),
            resultSet.getString("duration"), 
            resultSet.getDate("date"),resultSet.getTime("time"), 
            resultSet.getString("description"),
            resultSet.getString("path_pict"));
    }

    public void updateActivity(int id_activity, String title, String kind, Double distance, String duration, Date date, Time time, String description, String path_pict) {
        String sql = "UPDATE Activity SET title = ?, kind = ?, distance = ?, duration = ?, date = ?, time = ?, description = ?, path_pict = ? WHERE id_activity = ?";

        jdbcTemplate.update(sql, title, kind, distance, duration, date, time, description, path_pict, id_activity);
    }
}
