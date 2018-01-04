package com.cym.security.browser.config;

import com.cym.security.browser.filter.sms.SmsCodeAuthenticationFilter;
import com.cym.security.browser.provider.SmsCodeAuthenticationProvider;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/31 23:57
 */

/**
 * 该配置是{@link BrowserSecurityConfig} 的一个子配置
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private AuthenticationSuccessHandler cymAuthenticationSuccessHandle;

    @Autowired
    private AuthenticationFailureHandler cymAuthenticationFailHandle;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        //设置AuthenticationManager(ProviderManager)
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置处理成功失败实现类
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(cymAuthenticationSuccessHandle);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(cymAuthenticationFailHandle);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(myUserDetailsService);

        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
