package com.cym.security.app.config;

import com.cym.security.app.filter.SmsCodeFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/6 19:26
 */
@Configuration
@EnableResourceServer //资源服务
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    private Logger logger = Logger.getLogger(this.getClass());

    private final String DEFAULTURL = "/authentication/require";
    @Autowired
    private AuthenticationSuccessHandler cymAuthenticationSuccessHandle;

    @Autowired
    private AuthenticationFailureHandler cymAuthenticationFailHandle;

    @Autowired
    private  RedisTemplate redisTemplate;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.info("==========安全验证ResourceServerConfig的configure方法======================");
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(redisTemplate,cymAuthenticationFailHandle);

        http
                //添加过滤器
                .addFilterBefore(smsCodeFilter, SecurityContextPersistenceFilter.class)
                .authorizeRequests()//授权
                .antMatchers(DEFAULTURL,
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
