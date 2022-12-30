package com.woody.productwarehousingapi.configuration;

import com.woody.productwarehousingapi.entrypoint.MyAuthenticationEntryPoint;
import com.woody.productwarehousingapi.filter.JsonUsernamePasswordAuthenticationFilter;
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
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //關閉防護(這樣就不用token)，測試可以寫，正式的話要註解掉
        http.csrf().disable();

        //初始登入處理
        http.addFilterBefore(new JsonUsernamePasswordAuthenticationFilter(authenticationManager()),
                UsernamePasswordAuthenticationFilter.class);

        //異常處理
        http.exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())//處理未登入前使用API的錯誤
                .accessDeniedHandler(new MyAccessDeniedHandler());//處理無權限使用API的錯誤

        //HTTP請求保護
        http.authorizeRequests()
//                .antMatchers("/api").hasIpAddress("127.0.0.1")//限制只有從IP地址為127.0.0.1 的請求才能訪問 /api路徑
                .antMatchers("/login").permitAll()//不需要被認證的頁面
                .antMatchers("/logout").permitAll()//不需要被認證的頁面
                .antMatchers("/order").hasAnyAuthority("admin", "normal")
                .anyRequest().authenticated();//其他都要被驗證

        //登出設定
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置身份驗證
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        String sql = "SELECT account, password, 'true' as enabled FROM user_list WHERE account = ?";
        String authorSql = "SELECT account, role FROM user_list WHERE account = ?";

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(sql)
                .authoritiesByUsernameQuery(authorSql);
    }
}
