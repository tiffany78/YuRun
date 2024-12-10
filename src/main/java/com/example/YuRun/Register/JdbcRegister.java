package com.example.YuRun.Register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRegister implements RegisterRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void tambahUser(RegisterUser user) {
        try {
            String sql = "INSERT INTO Users (name, email, password, isAdmin, status) VALUES (?, ?, ?, B'0', ?)";
            
            int rowsAffected = jdbcTemplate.update(sql, 
                user.getName(), 
                user.getEmail(), 
                user.getPassword(), 
                user.isStatus()
            );
            
            if (rowsAffected == 0) {
                throw new RuntimeException("No rows were inserted");
            }
        } catch (DataIntegrityViolationException e) {
            // Tangani error unique constraint (email sudah ada)
            throw new RuntimeException("Email already exists", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to insert user: " + e.getMessage(), e);
        }
    }
}