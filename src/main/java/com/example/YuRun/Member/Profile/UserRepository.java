package com.example.YuRun.Member.Profile;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class UserRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByUsername(String username) {
        String sql = """
            SELECT name, email, password FROM Users WHERE username = ?
        """;
        return jdbcTemplate.queryForObject(sql, new Object[] { username }, (rs, rowNum) -> {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }

    
    public String getPasswordByName(String name) {
        String sql = """
        SELECT password FROM Users WHERE name = ?
        """;
        return jdbcTemplate.queryForObject(sql, new Object[] { name }, String.class);
    }

    public int updatePassword(String name, String oldPassword, String newPassword) {
        // Ambil password lama dari database
        String currentPassword = getPasswordByName(name);

        // Periksa apakah password lama sesuai
        if (!currentPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("Password lama tidak sesuai.");
        }

        // Jika sesuai, lakukan update password
        String sql = "UPDATE Users SET password = ? WHERE name = ?";
        return jdbcTemplate.update(sql, newPassword, name);
    }

}