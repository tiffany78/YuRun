package com.example.YuRun.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<LoginUser> findPengguna (String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        List<LoginUser> results = jdbcTemplate.query(sql, this::mapToPengguna, email);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    private LoginUser mapToPengguna(ResultSet resultSet, int rowNum) throws SQLException {
        return new LoginUser(
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getInt("isadmin"),
                resultSet.getBoolean("status"),
                resultSet.getInt("id_user")
        );
    }
}
