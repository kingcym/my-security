package com.cym.security.browser.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/2 0:11
 */
public class CymSpringSocialConfigurer extends SpringSocialConfigurer {
    private String filterProcessUrl;

    @Autowired
    private AuthenticationSuccessHandler cymAuthenticationSuccessHandle;


    public CymSpringSocialConfigurer(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        filter.setAuthenticationSuccessHandler(cymAuthenticationSuccessHandle);
        return (T)filter;
    }
}
