package com.example.YuRun.Member.Progress;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.YuRun.Member.ActivityMember.ActivityMember;

@Repository
public class JDBCProgress implements ProgressRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<ActivityMember> getAllActivities(int id_user) {
        String sql = "SELECT * FROM Activity WHERE id_user = ? ORDER BY date";
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user);
    }

    private ActivityMember maptoRowActivityMember(ResultSet resultSet, int rowNum) throws SQLException {
        return new ActivityMember(
            resultSet.getInt("id_activity"), 
            resultSet.getInt("id_user"), 
            resultSet.getString("title"), 
            resultSet.getString("kind"), 
            resultSet.getDouble("distance"),
            resultSet.getString("duration"), 
            resultSet.getDate("date"),
            resultSet.getTime("time"), 
            resultSet.getString("description"),
            resultSet.getString("path_pict"));
    }

    public List<ProgressRace> getAllRace(int id_user){
        String sql = "SELECT * FROM show_race_member WHERE id_user = ?";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user);
    }

    private ProgressRace maptoRowRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new ProgressRace(
            resultSet.getInt("id_race"), 
            resultSet.getString("title"), 
            resultSet.getDate("race_date"),
            resultSet.getDouble("distance"),
            resultSet.getInt("id_user"),
            resultSet.getString("name"),
            resultSet.getString("member_duration"), 
            resultSet.getBoolean("status"));
    }
}
