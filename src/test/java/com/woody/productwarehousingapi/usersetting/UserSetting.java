package com.woody.productwarehousingapi.usersetting;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class UserSetting {

    /**
     * 設置使用者的帳號、密碼、權限，並放入RequestBuilder中
     */
    public static MockHttpServletRequestBuilder setUser(String account, String password, String authority, RequestBuilder requestBuilder) {
        UserDetails userDetails = User.withUsername(account)
                .password(password)
                .authorities(authority)
                .build();
        return ((MockHttpServletRequestBuilder) requestBuilder).with(user(userDetails));
    }
}
