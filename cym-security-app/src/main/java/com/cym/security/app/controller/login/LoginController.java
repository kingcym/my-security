package com.cym.security.app.controller.login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 13:12
 */
@RestController
public class LoginController {


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


    @GetMapping("/currentUser4")
    public Object currentUser4 (HttpServletRequest request) throws UnsupportedEncodingException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(authorization, "bearer ");
        Claims claims = Jwts.parser().setSigningKey("kingcym".getBytes("UTF-8")).parseClaimsJws(token).getBody();

        return claims;
    }
}
