package com.cym.security.browser.service;

import com.cym.security.browser.dto.MyUser;
import com.cym.security.browser.mapper.LoginMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 14:18
 */
@Component
public class MyUserDetailsService implements UserDetailsService,SocialUserDetailsService {
    private Logger logger = Logger.getLogger(this.getClass());

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private LoginMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        logger.info("====传入用户名======" + name);
        //根据用户名查找用户信息
        MyUser user = userMapper.getUser(name);
        if (user == null) {
            user = userMapper.getUserByPhone(name);
        }
        // PasswordEncoder接口是处理密码加密解密 todo
        String password = passwordEncoder.encode(user.getPassWord());
        logger.info("====加密后密码======" + password);
        //校验用户密码
        //User是security自己的类实现了UserDetails接口  UserDetails todo
        return new SocialUser(name, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("====传入用户id======" + userId);
        //根据用户名查找用户信息
        MyUser user = userMapper.getUser(userId);
        // PasswordEncoder接口是处理密码加密解密 todo
        String password = passwordEncoder.encode(user.getPassWord());
        logger.info("====加密后密码======" + password);
        //校验用户密码
        //User是security自己的类实现了UserDetails接口  UserDetails todo
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
