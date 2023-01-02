package com.woody.productwarehousingapi.dto;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty
    private String account;
    @NotEmpty
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
