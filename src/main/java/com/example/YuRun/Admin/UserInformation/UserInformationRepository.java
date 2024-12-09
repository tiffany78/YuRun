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
            SELECT name, email, status
            FROM User
            WHERE 1=1 AND isAdmin = '0'
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
            resultSet.getString("name"),
            resultSet.getString("email"),
            null, // Password tidak diambil dari query
            false, // isAdmin di-hardcode karena hanya non-admin yang diambil
            resultSet.getBoolean("status")
        );
    }

    public List<User> findByName(String filter) {
        String sql = """
            SELECT name, email, status
            FROM User
            WHERE 1=1 AND isAdmin = '0' AND name ILIKE ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User userMember = new User();
            userMember.setName(rs.getString("name"));
            userMember.setEmail(rs.getString("email"));
            userMember.setStatus(rs.getBoolean("status"));

            return userMember;
        }, "%" + filter + "%");
    }
}
