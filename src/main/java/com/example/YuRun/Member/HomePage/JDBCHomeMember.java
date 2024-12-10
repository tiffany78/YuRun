package com.example.YuRun.Member.HomePage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCHomeMember implements HomeMemberRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Activity> getActivity(String username){
        String sql = "SELECT activity.id_activity, activity.id_user, activity.title, activity.kind, activity.distance, activity.date, activity.time, activity.description, activity.duration::text AS duration FROM activity JOIN users ON users.id_user = activity.id_user WHERE users.name ILIKE ? ORDER BY activity.date DESC LIMIT 3";
    
        System.out.println("Executing query for username: " + username);
        List<Activity> activities = jdbcTemplate.query(sql, this::mapRowToActivity, username);
        System.out.println("Query executed. Number of activities: " + activities.size());
        return activities;
    }
    

    private Activity mapRowToActivity(ResultSet resultSet, int rowNum) throws SQLException {
        return new Activity(
            resultSet.getInt("id_activity"),
            resultSet.getInt("id_user"),
            resultSet.getString("title"),
            resultSet.getString("kind"),
            resultSet.getDouble("distance"),
            resultSet.getString("duration"),
            resultSet.getDate("date"),
            resultSet.getTime("time"),
            resultSet.getString("description")
        );
    }
    
}
