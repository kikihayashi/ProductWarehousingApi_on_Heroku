package com.woody.productwarehousingapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woody.productwarehousingapi.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 需要在 data.sql、schema.sql中建立使用者資料以及表
 * */
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void login_success() throws Exception {
        // 建立 UserDetails 的實作類別，並建立測試用的使用者資料
        UserDetails userDetails = User.withUsername("admin")
                .password("1234")
                .authorities("admin")
                .accountLocked(false)//使用者帳號鎖定設定，true代表鎖住
                .accountExpired(false)//使用者帳號過期設定，true代表過期
                .build();

        // 使用 RequestPostProcessor 類別的物件，呼叫 userDetails 方法，傳入 UserDetails 物件，模擬使用者登入的流程
        RequestPostProcessor requestPostProcessor = SecurityMockMvcRequestPostProcessors.user(userDetails);

        //建立錯誤密碼的json資料
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccount("admin");
        loginRequest.setPassword("1234");

        String json = objectMapper.writeValueAsString(loginRequest);

        //建立請求
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(requestPostProcessor);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status", equalTo("success")))
                .andExpect(jsonPath("$.message", equalTo("登入成功！")));
    }
    @Test
    public void login_incorrectPassword() throws Exception {
        // 建立 UserDetails 的實作類別，並建立測試用的使用者資料
        UserDetails userDetails = User.withUsername("admin")
                .password("1234")
                .authorities("admin")
                .build();

        // 使用 RequestPostProcessor 類別的物件，呼叫 userDetails 方法，傳入 UserDetails 物件，模擬使用者登入的流程
        RequestPostProcessor requestPostProcessor = SecurityMockMvcRequestPostProcessors.user(userDetails);

        //建立錯誤密碼的json資料
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccount("admin");
        loginRequest.setPassword("12345");

        String json = objectMapper.writeValueAsString(loginRequest);

        //建立請求
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(requestPostProcessor);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.status", equalTo("error")))
                .andExpect(jsonPath("$.message", equalTo("帳號密碼有誤！")));
    }
}
