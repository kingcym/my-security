package com.cym.security.browser.authentication;

import com.cym.security.browser.enums.LoginType;
import com.cym.security.browser.properties.BrowserProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
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
 * 处理登陆成功
 * 1.只需继承AuthenticationSuccessHandler接口
 * 2.这里我们继承默认的实现类,通过判断是返回json还是默认的跳转
 */
@Component("cymAuthenticationSuccessHandle")
public class CymAuthenticationSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = Logger.getLogger(this.getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BrowserProperties browserProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("===============登录成功===========");
        if (LoginType.JSON.equals(browserProperties.getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            String authen = objectMapper.writeValueAsString(authentication);
            logger.info("authentication : " + authen);
            response.getWriter().write(authen);
        } else {
            //SavedRequest  todo
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if(savedRequest == null){
                //返回默认的登陆成功页面
                this.getRedirectStrategy().sendRedirect(request, response, "/html/success.html");
            } else {
                //执行父类默认方法
                //返回之前中断的请求
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }
}
