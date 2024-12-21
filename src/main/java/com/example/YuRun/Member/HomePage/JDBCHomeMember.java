package com.example.YuRun.Member.HomePage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCHomeMember implements HomeMemberRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Activity> getActivity(String username){
        String sql = "SELECT activity.id_activity, activity.id_user, activity.title, activity.kind, activity.distance, activity.date, activity.time, activity.description, activity.duration FROM activity JOIN users ON users.id_user = activity.id_user WHERE users.name ILIKE ? ORDER BY activity.date DESC LIMIT 3";
    
        List<Activity> activities = jdbcTemplate.query(sql, this::mapRowToActivity, username);
        return activities;
    }
    
    public List<Activity> getActivityAll(String username){
        String sql = "SELECT activity.id_activity, activity.id_user, activity.title, activity.kind, activity.distance, activity.date, activity.time, activity.description, activity.duration FROM activity JOIN users ON users.id_user = activity.id_user WHERE users.name ILIKE ? ORDER BY activity.date DESC";
    
        List<Activity> activities = jdbcTemplate.query(sql, this::mapRowToActivity, username);
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
    
    public List<RaceUser> getMyRace(String user) {
        String sql = "select race.id_race, race.title, race.start_date, race.time, race.distance, race.description from joinrace join users on users.id_user = joinrace.id_user join race on race.id_race = joinrace.id_race where users.name ilike ? LIMIT 3";
        return jdbcTemplate.query(sql, this::mapRowToRaceHome, user);
    } 

    private RaceUser mapRowToRaceHome(ResultSet resultSet, int rowNum) throws SQLException {
        return new RaceUser(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("start_date"),
            resultSet.getTime("time"),
            resultSet.getDouble("distance"),
            resultSet.getString("description")
        );
    }

    public List<RaceUser> getUpRace(String user){
        String sql = "SELECT race.id_race, race.title, race.start_date, race.time, race.distance, race.description FROM race LEFT JOIN joinrace ON race.id_race = joinrace.id_race LEFT JOIN users ON joinrace.id_user = users.id_user  WHERE users.name ILIKE ? OR joinrace.id_user IS NULL LIMIT 2";
        return jdbcTemplate.query(sql, this::mapRowToRaceHome, user);
    }
}
