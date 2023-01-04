package com.woody.productwarehousingapi.service;

import com.woody.productwarehousingapi.dao.LoginDao;
import com.woody.productwarehousingapi.dto.LoginItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 身份驗證管理器的實作方法一：需要實作UserDetailsService
 * 標記 @Service 讓Spring可以自動地將這個類別的實例納入Spring容器中
 */
@Service
public class LoginDetailService implements UserDetailsService {
    @Autowired
    private LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginItem user = loginDao.findUserByAccount(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }

        String password = user.getPassword();
        String authority = user.getRole();

        return new User(
                username,
                password,
                AuthorityUtils.createAuthorityList(authority));
//                AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
    }
}
