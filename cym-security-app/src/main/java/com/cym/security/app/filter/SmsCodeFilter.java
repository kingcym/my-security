package com.cym.security.app.filter;

import com.cym.security.app.controller.sms.SMSCodeController;
import com.cym.security.app.dto.SmsCode;
import com.cym.security.app.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/29 21:51
 */

//OncePerRequestFilter todo

public class SmsCodeFilter extends SecurityContextPersistenceFilter {
    private Logger logger = Logger.getLogger(this.getClass());

    private AuthenticationFailureHandler authenticationFailureHandler;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final RedisTemplate redisTemplate;

    public SmsCodeFilter(RedisTemplate redisTemplate,AuthenticationFailureHandler authenticationFailureHandler) {
        this.redisTemplate = redisTemplate;
        this.authenticationFailureHandler=authenticationFailureHandler;
    }


    //判断验证码是否正确
    private void validdate(HttpServletRequest request) throws ServletRequestBindingException, ValidateCodeException {

        String cid = ((HttpServletRequest)request).getHeader("cid");

        String key = SMSCodeController.MOBILECODEKEY + cid;
        SmsCode imageCode =(SmsCode) redisTemplate.opsForValue().get(key);
        //ServletRequestUtils todo

        //获取请求中验证码
        String code = request.getParameter("smsCode");
        if (StringUtils.isBlank(code) ){
            logger.info("===========验证码不能为空=====================");
            throw new ValidateCodeException("验证码不能为空");
        }
        if (imageCode == null ){
            logger.info("===========验证码不存在=====================");
            throw new ValidateCodeException("验证码不存在");
        }
        if (imageCode.isExpried() ){
            logger.info("===========验证码已过期=====================");
            redisTemplate.delete(key);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(imageCode.getCode(),code) ){
            logger.info("===========验证码不匹配=====================");
            throw new ValidateCodeException("验证码不匹配");
        }
        redisTemplate.delete(key);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        logger.info("===========进入SmsCodeFilter过滤器=====================");
        boolean action =false;

        if (antPathMatcher.match("/oauth/token",request.getRequestURI())){
            if (StringUtils.isNotBlank(request.getParameter("smsCode"))){
                action = true;

            }
        }


        //判断是不是登陆请求
        if (action){
            try {
                //验证验证码
                validdate(request);

            } catch (ValidateCodeException e) {
                //交给登陆异常处理
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

}
