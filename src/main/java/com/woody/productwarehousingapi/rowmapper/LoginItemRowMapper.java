package com.woody.productwarehousingapi.rowmapper;


import com.woody.productwarehousingapi.dto.LoginItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginItemRowMapper implements RowMapper<LoginItem> {
    @Override
    public LoginItem mapRow(ResultSet resultSet, int i) throws SQLException {
        LoginItem loginItem = new LoginItem();
        loginItem.setName(resultSet.getString("user_name"));
        loginItem.setAccount(resultSet.getString("account"));
        loginItem.setPassword(resultSet.getString("password"));
        loginItem.setRole(resultSet.getString("role"));
        return loginItem;
    }
}