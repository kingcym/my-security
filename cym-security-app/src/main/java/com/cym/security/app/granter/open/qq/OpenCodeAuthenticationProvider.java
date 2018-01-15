package com.cym.security.app.granter.open.qq;

import com.cym.security.app.granter.sms.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/31 23:13
 */
public class OpenCodeAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService socialUserDetailsService;

    public OpenCodeAuthenticationProvider(SocialUserDetailsService socialUserDetailsService) {
        this.socialUserDetailsService = socialUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenCodeAuthenticationToken authenticationToken =(OpenCodeAuthenticationToken)authentication;
        //传入的是token
        UserDetails user = socialUserDetailsService.loadUserByUserId((String) authenticationToken.getPrincipal());
        if (user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        //授权
        OpenCodeAuthenticationToken result = new OpenCodeAuthenticationToken(user, user.getAuthorities());
        //把之前未授权SmsCodeAuthenticationToken的信息复制
        result.setDetails(authenticationToken.getDetails());
        return result;
    }


    /**
     * 由SmsCodeAuthenticationToken选择对应的SmsCodeAuthenticationProvider
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
