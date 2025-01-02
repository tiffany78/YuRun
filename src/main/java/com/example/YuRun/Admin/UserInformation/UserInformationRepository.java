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

    public List<User> findAll(String filter, String statusMember, int entries, int offset) {
        boolean statusBoolean = false;
        if(statusMember.equals("true")){
            statusBoolean = true;
        }

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
        if(statusMember != null && !statusMember.equals("null")){
            sql += " AND status = ?";
            params.add(statusBoolean);
        }

        if(entries > 0){
            sql += " LIMIT ? OFFSET ?";
            params.add(entries);
            params.add(offset);
        }
    
        return jdbcTemplate.query(sql, this::mapRowToUserInformation, params.toArray());
    }

    public int getTotalEntries(String filter, String statusMember) {
        boolean statusBoolean = false;
        if(statusMember.equals("true")){
            statusBoolean = true;
        }

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
        if(statusMember != null && !statusMember.equals("null")){
            sql += " AND status = ?";
            params.add(statusBoolean);
        }
    
        List<User> list = jdbcTemplate.query(sql, this::mapRowToUserInformation, params.toArray());

        return list.size();
    }
    
    private User mapRowToUserInformation(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
            resultSet.getLong("row_num"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            null,
            false,
            resultSet.getBoolean("status")
        );
    }

    public int updateUserStatusByName(String name, boolean newStatus) {
        String sql = "UPDATE Users SET status = ? WHERE name = ?";
        return jdbcTemplate.update(sql, newStatus, name);
    }
}
