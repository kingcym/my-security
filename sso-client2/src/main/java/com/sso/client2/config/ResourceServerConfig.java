package com.sso.client2.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/6 19:26
 */
@Configuration
@EnableResourceServer //资源服务
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    private Logger logger = Logger.getLogger(this.getClass());
    private final String DEFAULHTML = "/html/index.html";

    private final String DEFAULTURL = "/authentication/require";

    @Autowired
    private  RedisTemplate redisTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        logger.info("==========安全验证ResourceServerConfig的configure方法======================");
//
//
//            http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不创建也不用session
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")
//                .antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('write')");
//




        http
                .authorizeRequests()//授权
                .antMatchers(DEFAULTURL,DEFAULHTML,
                        "/code/*",
                        "/user/regist",
                        "/register").permitAll() //DEFAULTURL这个路径不需要认证
                .anyRequest() //所有请求
                .authenticated()//都需要身份认
                .and()
                .csrf().disable();//csrf防护功能关闭
        //             .apply(smsCodeAuthenticationSecurityConfig)
        //            .and()
        //            .apply(cymSpringSocialConfigurer);
        logger.info("==========安全验证ResourceServerConfig的configure方法====end==================");

    }




}
