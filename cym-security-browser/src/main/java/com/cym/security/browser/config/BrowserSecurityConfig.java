package com.cym.security.browser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 23:14
 */
/**
 * pring Security配置
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()  //弹出框验证
        http.formLogin()    //表单验证
                .and()
                .authorizeRequests()//授权
                .anyRequest() //所有请求
                .authenticated();//都需要身份认
    }
}
