package com.example.YuRun.Member.ActivityMember;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCActivityMember implements AddActivityRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addActivity(int id_user, String title, String kind, Double distance, Date date, Time time, String description, byte[] picture){
        String sql = "INSERT INTO Activity(id_user, title, kind, distance, date, time, description, picture) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
        id_user, title, kind, distance, date, time, description, picture);
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
            resultSet.getString("description"), resultSet.getBytes("picture"));
    }
}
