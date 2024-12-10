package com.example.YuRun.Admin.UserInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserInformationRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserInformationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll(String filter) {
        String sql = """
            SELECT ROW_NUMBER() OVER () AS row_num, name, email, isAdmin, status
            FROM Users
            WHERE isAdmin = '0'
        """;
        List<Object> params = new ArrayList<>();
        if (filter != null && !filter.isEmpty()) {
            sql += " AND name ILIKE ?";
            params.add("%" + filter + "%");
        }
    
        return jdbcTemplate.query(sql, this::mapRowToUserInformation, params.toArray());
    }
    
    private User mapRowToUserInformation(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getLong("row_num"),  // ID urut dimulai dari 1
            resultSet.getString("name"),
            resultSet.getString("email"),
            null, // Password tidak diambil dari query
            false, // isAdmin di-hardcode karena hanya non-admin yang diambil
            resultSet.getBoolean("status")
        );
    }

    public int updateUserStatusByName(String name, boolean newStatus) {
        String sql = "UPDATE Users SET status = ? WHERE name = ?";
        return jdbcTemplate.update(sql, newStatus, name);
    }
}
