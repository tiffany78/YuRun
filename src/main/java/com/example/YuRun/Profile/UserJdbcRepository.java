package com.example.YuRun.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class UserJdbcRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (ResultSet rs, int rowNum) -> {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    };

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", userRowMapper, email);
        } catch (Exception e) {
            return null; // Jika tidak ada pengguna ditemukan dengan email tersebut
        }
    }

    @Override
    public void updateName(String email, String newName) {
        // Menghapus pengecekan password
        jdbcTemplate.update("UPDATE users SET name = ? WHERE email = ?",
                newName, email);
    }
    
    @Override
    public void updateEmail(String email, String newEmail) {
        User currentUser = findByEmail(email);
        if (currentUser != null && !currentUser.getEmail().equals(newEmail)) {
            // Update email hanya jika email baru berbeda dengan yang lama
            jdbcTemplate.update("UPDATE users SET email = ? WHERE email = ?",
                    newEmail, email);
        } else if (currentUser == null) {
            throw new IllegalArgumentException("User not found.");
        } else {
            throw new IllegalArgumentException("New email is the same as the current email.");
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        // Menghapus pengecekan password
        jdbcTemplate.update("UPDATE users SET password = ? WHERE email = ?",
                newPassword, email);
    }
}
