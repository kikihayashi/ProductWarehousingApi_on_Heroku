package com.woody.productwarehousingapi.dao;


import com.woody.productwarehousingapi.dto.LoginItem;

public interface LoginDao {
    LoginItem findUserByAccount(String account);
}
