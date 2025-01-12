package com.example.YuRun.Admin.Homepage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCHomeImplementation implements HomeRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Race> findRace() {
        LocalDate currDate = LocalDate.now();
        String sql = "SELECT * FROM race WHERE end_date > ? ORDER BY end_date LIMIT 3";
        return jdbcTemplate.query(sql, this::mapRowToRaceHome, currDate);
    } 

    public List<Race> getRaceLandingPage() {
        String sql = "SELECT * FROM race ORDER BY end_date DESC LIMIT 3";
        return jdbcTemplate.query(sql, this::mapRowToRaceHome);
    }

    private Race mapRowToRaceHome(ResultSet resultSet, int rowNum) throws SQLException {
        return new Race(
            resultSet.getInt("id_race"),
            resultSet.getString("title"),
            resultSet.getDate("end_date"),
            resultSet.getDouble("distance"),
            resultSet.getString("description"),
            null
        );
    }

    @Override
    public List<JoinRace> countRace() {
        String sql = "SELECT title, count FROM count_race_admin WHERE status = false ORDER BY end_date";
        return jdbcTemplate.query(sql, this::mapRowToRace);
    }

    private JoinRace mapRowToRace(ResultSet resultSet, int rowNum) throws SQLException {
        return new JoinRace(
            resultSet.getString("title"),
            resultSet.getInt("count")
        );
    }

    public List<MonthlyActivity> getMonthlyActivities() {
        String sql = "SELECT TO_CHAR(date, 'Month YYYY') as month, COUNT(*) as activity_count " +
                    "FROM Activity " +
                    "GROUP BY TO_CHAR(date, 'Month YYYY'), DATE_TRUNC('month', date) " +
                    "ORDER BY DATE_TRUNC('month', MIN(date))";
        return jdbcTemplate.query(sql, this::mapRowToMonthlyActivity);
    }

    private MonthlyActivity mapRowToMonthlyActivity(ResultSet resultSet, int rowNum) throws SQLException {
        return new MonthlyActivity(
            resultSet.getString("month"),
            resultSet.getInt("activity_count")
        );
    }

    public List<ActivityTypeCount> getActivityTypeCounts() {
        String sql = "SELECT kind as activity_type, COUNT(*) as count FROM Activity GROUP BY kind";
        return jdbcTemplate.query(sql, this::mapRowToActivityTypeCount);
    }

    private ActivityTypeCount mapRowToActivityTypeCount(ResultSet resultSet, int rowNum) throws SQLException {
        return new ActivityTypeCount(
            resultSet.getString("activity_type"),
            resultSet.getInt("count")
        );
    }
}