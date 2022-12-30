package com.woody.productwarehousingapi.dao.impl;

import com.woody.productwarehousingapi.dao.LoginDao;
import com.woody.productwarehousingapi.dto.LoginItem;
import com.woody.productwarehousingapi.rowmapper.LoginItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginDaoImpl implements LoginDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public LoginItem findUserByAccount(String account) {
        System.out.println("findUserByAccount");
        String sqlCommand = "SELECT user_name, account, password, role " +
                "FROM user_list " +
                "WHERE account = :account";

        Map<String, Object> map = new HashMap<>();
        map.put("account", account);

        try {
            List<LoginItem> userList = namedParameterJdbcTemplate.query(sqlCommand, map, new LoginItemRowMapper());
            if (userList.size() > 0) {

                return userList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
