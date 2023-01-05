package com.woody.productwarehousingapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 如果你在 WebSecurityConfigurerAdapter 的 configure 方法中設定了一個 LogoutSuccessHandler，
 * 那麼在 Controller層的 logout 方法中就不會再被呼叫。這是因為 LogoutSuccessHandler
 * 負責處理登出成功後的行為，所以在使用者登出成功後，就會直接呼叫 LogoutSuccessHandler 的
 * onLogoutSuccess 方法，而不會再繼續執行 Controller 層的 logout 方法。
 * */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, String> result = new HashMap<>();
        result.put("message", "登出成功！");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
