package com.cym.security.browser.controller.login;

import com.cym.security.browser.dto.MyUser;
import org.omg.IOP.ServiceContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 13:12
 */
@RestController
public class LoginController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/user/regist")
    public void regist (MyUser user, HttpServletRequest request){
        String userId = user.getUsername();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        String providerId = connection.getKey().getProviderId();
        String ProviderUserId = connection.getKey().getProviderUserId();
        String DisplayName = connection.getDisplayName();
        String ImageUrl = connection.getImageUrl();
        //直接入库
        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
    }


    @GetMapping("/login")
    public String hello1 (){
        return "aaa";
    }

    @GetMapping("/currentUser1")
    public Object currentUser1 (){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/currentUser2")
    public Object currentUser2 (Authentication authentication){
        return authentication;
    }

    @GetMapping("/currentUser3")
    public Object currentUser3 (@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }

}
