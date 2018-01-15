package com.cym.security.app.service;

import com.cym.security.app.dto.CymSocialUser;
import com.cym.security.app.dto.MyUser;
import com.cym.security.app.exception.ValidateCodeException;
import com.cym.security.app.granter.CustomerException;
import com.cym.security.app.granter.open.qq.QQOAuth2Template;
import com.cym.security.app.granter.open.qq.opentype.WhetherBound;
import com.cym.security.app.mapper.LoginMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.oauth2.AccessGrant;
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

    @Autowired
    WhetherBound whetherBound;

    @Override
    public UserDetails loadUserByUsername(String name)  {
        logger.info("====传入用户名======" + name);
        //根据用户名查找用户信息
        MyUser user = userMapper.getUser(name);
        if (user == null) {
            user = userMapper.getUserByPhone(name);
        }
        if (user == null) {
            throw new CustomerException(400,"用户名未找到");
        }
        // PasswordEncoder接口是处理密码加密解密 todo
        String password = passwordEncoder.encode(user.getPassWord());
        logger.info("====加密后密码======" + password);
        //校验用户密码
        //User是security自己的类实现了UserDetails接口  UserDetails todo
        return new CymSocialUser(user.getAge(),user.getMobile(),name, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String code) throws UsernameNotFoundException {
        logger.info("====传入code======" + code);
        //回调地址
        String redirectUri = "http://www.pinzhi365.com/qqLogin/callback.do";
        /**
         * 获取sccessToken
         */
        //获取token的地址
        String accessTokenUrl = "https://graph.qq.com/oauth2.0/token";
        QQOAuth2Template qqoAuth2Template = new QQOAuth2Template("100550231", "69b6ab57b22f3c2fe6a6149274e3295e", "", accessTokenUrl);
        AccessGrant accessGrant = qqoAuth2Template.exchangeForAccess(code, redirectUri, null);
        /**
         * 获取openid
         */
        //获取openid地址
        String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
        String url = String.format(URL_GET_OPENID, accessGrant.getAccessToken());
        String result = qqoAuth2Template.getRestTemplate().getForObject(url, String.class);
        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        logger.info("======QQ请求openId返回信息===========：" + result);
        /**
         * 获取用户信息
         */
        //获取用户信息 地址
        String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
        String userUrl = String.format(URL_GET_USERINFO,accessGrant.getAccessToken() ,"100550231", openId);
        String userInfo = qqoAuth2Template.getRestTemplate().getForObject(userUrl, String.class);
        logger.info("======QQ请求获取用户信息===========：" + userInfo);
        if (whetherBound.whetherBound("qq",openId)){
            throw new ValidateCodeException(555,"请绑定账号信息 ");
        }

        return new SocialUser("李四", "111",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }
}
