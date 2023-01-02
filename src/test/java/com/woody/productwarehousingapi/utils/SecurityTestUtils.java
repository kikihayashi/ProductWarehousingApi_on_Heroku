package com.woody.productwarehousingapi.utils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public class SecurityTestUtils {

    /**
     * "不"需要在 data.sql中建立使用者資料，此為虛擬的資料
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
