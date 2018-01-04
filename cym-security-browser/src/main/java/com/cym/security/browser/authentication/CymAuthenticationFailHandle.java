package com.cym.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 23:42
 */

/**
 * 处理登陆失败
 */
@Component("cymAuthenticationFailHandle")
public class CymAuthenticationFailHandle implements AuthenticationFailureHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException authenticationException) throws IOException, ServletException {
        logger.info("===============登录失败===========");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        String exception = objectMapper.writeValueAsString(authenticationException.getMessage());
        logger.info("authentication : " + exception);
        response.getWriter().write(exception);
    }
}
