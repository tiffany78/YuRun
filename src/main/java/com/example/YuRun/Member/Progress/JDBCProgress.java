package com.example.YuRun.Member.Progress;

import java.sql.Date;
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

    public List<ActivityMember> getAllActivities(int id_user, String sort) {
        String sql = "SELECT * FROM Activity WHERE id_user = ?";

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
        
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user);
    }

    public List<ActivityMember> getWeeklyActivities(int id_user) {
        String sql = "SELECT * FROM Activity WHERE id_user = ? AND date >= CURRENT_DATE - INTERVAL '7 days' ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user);
    }
    
    public List<ActivityMember> getMonthlyActivities(int id_user) {
        String sql = "SELECT * FROM Activity WHERE id_user = ? AND date >= CURRENT_DATE - INTERVAL '1 month' ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user);
    }
    
    public List<ActivityMember> getThreeMonthlyActivities(int id_user) {
        String sql = "SELECT * FROM Activity WHERE id_user = ? AND date >= CURRENT_DATE - INTERVAL '3 months' ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user);
    }
    
    public List<ActivityMember> getYearlyActivities(int id_user) {
        String sql = "SELECT * FROM Activity WHERE id_user = ? AND date >= CURRENT_DATE - INTERVAL '1 year' ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user);
    }  
    
    public List<ActivityMember> getActivitiesByDateRange(int id_user, Date startDate, Date endDate) {
        String sql = "SELECT * FROM Activity WHERE id_user = ? AND date BETWEEN ? AND ? ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::maptoRowActivityMember, id_user, startDate, endDate);
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
        String sql = "SELECT * FROM show_race_member WHERE id_user = ? AND member_duration != 'null' AND status = TRUE ORDER BY race_date DESC";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user);
    }

    public List<ProgressRace> getWeeklyRace(int id_user) {
        String sql = "SELECT * FROM show_race_member WHERE id_user = ? AND race_date >= CURRENT_DATE - INTERVAL '7 days' AND member_duration != 'null' AND status = TRUE ORDER BY race_date DESC";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user);
    }
    
    public List<ProgressRace> getMonthlyRace(int id_user) {
        String sql = "SELECT * FROM show_race_member WHERE id_user = ? AND race_date >= CURRENT_DATE - INTERVAL '1 month' AND member_duration != 'null' AND status = TRUE ORDER BY race_date DESC";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user);
    }
    
    public List<ProgressRace> getThreeMonthlyRace(int id_user) {
        String sql = "SELECT * FROM show_race_member WHERE id_user = ? AND race_date >= CURRENT_DATE - INTERVAL '3 months' AND member_duration != 'null' AND status = TRUE ORDER BY race_date DESC";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user);
    }
    
    public List<ProgressRace> getYearlyRace(int id_user) {
        String sql = "SELECT * FROM show_race_member WHERE id_user = ? AND race_date >= CURRENT_DATE - INTERVAL '1 year' AND member_duration != 'null' AND status = TRUE ORDER BY race_date DESC";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user);
    }  
    
    public List<ProgressRace> getRaceByDateRange(int id_user, Date startDate, Date endDate) {
        String sql = "SELECT * FROM show_race_member WHERE id_user = ? AND race_date BETWEEN ? AND ? AND member_duration != 'null' AND status = TRUE ORDER BY race_date DESC";
        return jdbcTemplate.query(sql, this::maptoRowRace, id_user, startDate, endDate);
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
