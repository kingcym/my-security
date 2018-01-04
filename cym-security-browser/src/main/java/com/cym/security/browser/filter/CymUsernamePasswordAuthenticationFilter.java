package com.cym.security.browser.filter;

import com.cym.security.browser.exception.ValidateCodeException;
import com.cym.security.browser.dto.ImageCode;
import com.cym.security.browser.controller.validateCode.ValidateController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/30 13:09
 */
public class CymUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private Logger logger = Logger.getLogger(this.getClass());

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("=========进入CymUsernamePasswordAuthenticationFilter=================");
        //验证验证码
        try {
            validdate(new ServletWebRequest(request));
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }

        return super.attemptAuthentication(request, response);
    }


    //判断验证码是否正确
    private void validdate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {
        ImageCode imageCode =(ImageCode) sessionStrategy.getAttribute(request, ValidateController.IMAGECODEKEY);
        //ServletRequestUtils todo

        //获取请求中验证码
        String code = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
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
            sessionStrategy.removeAttribute(request, ValidateController.IMAGECODEKEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equals(imageCode.getCode(),code) ){
            logger.info("===========验证码不匹配=====================");
            throw new ValidateCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ValidateController.IMAGECODEKEY);
    }


    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

}
