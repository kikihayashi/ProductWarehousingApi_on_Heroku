package com.woody.productwarehousingapi.service.impl;

import com.woody.productwarehousingapi.dao.LoginDao;
import com.woody.productwarehousingapi.dto.LoginItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginDetailServiceImpl implements UserDetailsService {
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
