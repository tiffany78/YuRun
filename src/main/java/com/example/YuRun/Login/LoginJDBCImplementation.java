package com.example.YuRun.Login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginJDBCImplementation implements LoginRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<LoginUser> findPengguna(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapToPengguna, email);
    }    

    private LoginUser mapToPengguna(ResultSet resultSet, int rowNum) throws SQLException{
        return new LoginUser(
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getBoolean("isadmin"),
            resultSet.getBoolean("status")
        );
    } 
}
