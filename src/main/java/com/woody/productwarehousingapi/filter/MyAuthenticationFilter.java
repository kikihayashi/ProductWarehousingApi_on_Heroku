package com.woody.productwarehousingapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woody.productwarehousingapi.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String POST = "POST";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private ObjectMapper objectMapper = new ObjectMapper();

    public MyAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/login");
        setAuthenticationManager(authenticationManager);
    }

    /**
     * 在這個過濾器中，我們首先檢查請求是否是POST請求。如果不是，則拋出AuthenticationServiceException異常。
     * 然後，我們使用ObjectMapper將請求正文解析為一個LoginRequest對象，該對象包含用戶名和密碼。
     * 接下來，我們檢查用戶名和密碼是否都存在，如果不存在，則拋出相應的異常。
     * 最後，我們使用用戶名和密碼創建一個新的UsernamePasswordAuthenticationToken對象，並使用身份驗證管理器調用authenticate方法進行身份驗證。
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

        if (loginRequest == null) {
            throw new AuthenticationServiceException("Missing login request");
        }

        String username = loginRequest.getAccount();
        String password = loginRequest.getPassword();

        if (username == null) {
            throw new AuthenticationServiceException("Missing username");
        }

        if (password == null) {
            throw new AuthenticationServiceException("Missing password");
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 在這個過濾器中，我們實現了successfulAuthentication和unsuccessfulAuthentication方法。
     * 如果身份驗證成功，則successfulAuthentication方法將身份驗證結果設置到安全上下文中，然後將請求轉發到應用程序的其他部分。
     * 如果身份驗證失敗，則unsuccessfulAuthentication方法將清除安全上下文，並將HTTP 401 Unauthorized錯誤返回給客戶端。
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //將用戶的身份驗證信息存儲到 SecurityContext 中，以便在其他部分使用。
        SecurityContextHolder.getContext().setAuthentication(authResult);
//        //用於將請求和回應傳遞給下一個過濾器
//        chain.doFilter(request, response);

        //將自訂的 JSON 資料寫入回應
        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "登入成功！");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(CONTENT_TYPE);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }


    /**
     * 類似AuthenticationFailureHandler裡的onAuthenticationFailure方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        //清除SecurityContext中的身份驗證信息
        SecurityContextHolder.clearContext();

        //將自訂的 JSON 資料寫入回應
        Map<String, String> result = new HashMap<>();
        result.put("status", "error");
        result.put("message", "帳號密碼有誤！");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
        objectMapper.writeValue(response.getOutputStream(), result);
    }
}
