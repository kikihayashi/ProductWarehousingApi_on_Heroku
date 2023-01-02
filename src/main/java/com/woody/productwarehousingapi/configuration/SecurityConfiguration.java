package com.woody.productwarehousingapi.configuration;

import com.woody.productwarehousingapi.entrypoint.MyAuthenticationEntryPoint;
import com.woody.productwarehousingapi.filter.MyAuthenticationFilter;
import com.woody.productwarehousingapi.handler.MyAccessDeniedHandler;
import com.woody.productwarehousingapi.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 若要自訂登入邏輯要繼承WebSecurityConfigurerAdapter
 * Controller層裡使用PreAuthorize前，需再SecurityConfiguration中加上
 * EnableWebSecurity、EnableGlobalMethodSecurity(prePostEnabled = true)這兩個註釋
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    /**
     * 一定要建立密碼演算的實例(密碼解密)
     * 通常在 configure(AuthenticationManagerBuilder auth) 方法內部使用。
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Step.1 設定的身份驗證管理器
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置身份驗證
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        String sql = "SELECT account, password, enable FROM user_list WHERE account = ? AND enable = true";
        String authorSql = "SELECT account, role FROM user_list WHERE account = ?";

        auth.jdbcAuthentication()//設定身份驗證管理器的方法
                .dataSource(dataSource)
                .usersByUsernameQuery(sql)
                .authoritiesByUsernameQuery(authorSql);
    }

    /**
     * Step.2 設定的 HTTP 安全性設定
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //關閉防護(這樣就不用token)，測試可以寫，正式的話要註解掉
        http.csrf().disable();

        //Step.1 初始登入處理，當客戶端傳送請求時，會先執行過濾器(帳號密碼驗證)
        http.addFilterBefore(new MyAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        //Step.2 HTTP請求保護，接著會執行設定的權限要求
        //注意設定的限制，會根據出現順序依次執行。
        http.authorizeRequests()
                .antMatchers("/login").permitAll()//不需要被認證的頁面
                .antMatchers("/logout").permitAll()//不需要被認證的頁面
//                .antMatchers("/api").hasIpAddress("127.0.0.1")//限制只有從IP地址為127.0.0.1 的請求才能訪問 /api路徑
//                .antMatchers("/order").hasAnyAuthority("admin", "normal")
//                .antMatchers("/info").hasAuthority("admin")
                .anyRequest().authenticated();//其他都要被驗證

        //Step.3 異常處理，如果使用者沒有擁有足夠的權限，則會執行authenticationEntryPoint設定的驗證入口處理。
        //如果使用者已經登入，但是仍然沒有足夠的權限訪問資源，則會執行 accessDeniedHandler 設定的權限拒絕處理。
        http.exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())//處理未登入前使用API的錯誤
                .accessDeniedHandler(new MyAccessDeniedHandler());//處理登入後無權限使用API的錯誤

        //登出設定
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }
}
