package com.example.YuRun.Register;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcRegister implements RegisterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void tambahUser(RegisterUser user) {
        String sql = "INSERT INTO users (name, email, password, isAdmin, status) VALUES (?, ?, ?, CAST(? AS BIT), ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(),'0',user.isStatus());
    }

    @Override
    public Optional<RegisterUser> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE name = ?";
        List<RegisterUser> results = jdbcTemplate.query(sql, this::mapRowToUser, username);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }


    private RegisterUser mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new RegisterUser(
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("password"),
            resultSet.getByte("isadmin"),
            resultSet.getBoolean("status")
        );
    }
    
}