package com.cym.security.browser.filter.sms;

import com.cym.security.browser.controller.sms.SMSCodeController;
import com.cym.security.browser.controller.validateCode.ValidateController;
import com.cym.security.browser.dto.ImageCode;
import com.cym.security.browser.dto.SmsCode;
import com.cym.security.browser.exception.ValidateCodeException;
import com.cym.security.browser.properties.BrowserProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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

public class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private Logger logger = Logger.getLogger(this.getClass());

    //操作session
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AuthenticationFailureHandler authenticationFailureHandler;

    private Set<String> urls = new HashSet<>();

    private BrowserProperties browserProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if (browserProperties.getSmsUrl() != null){
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(browserProperties.getSmsUrl(), ",");
            for (String configUrl : configUrls) {
                urls.add(configUrl);
            }
        }
        urls.add("/authentication/mobile");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("===========进入SmsCodeFilter过滤器=====================");
        boolean action =false;
        for (String url : urls) {
            if (antPathMatcher.match(url,request.getRequestURI())){
                action = true;
                break;
            }
        }

        //判断是不是登陆请求
        if (action){
            try {
                //验证验证码
                validdate(new ServletWebRequest(request));

            } catch (ValidateCodeException e) {
                //交给登陆异常处理
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);

    }

    //判断验证码是否正确
    private void validdate(ServletWebRequest request) throws ServletRequestBindingException, ValidateCodeException {
        SmsCode imageCode =(SmsCode) sessionStrategy.getAttribute(request, SMSCodeController.MOBILECODEKEY);
        //ServletRequestUtils todo

        //获取请求中验证码
        String code = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
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

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

    public BrowserProperties getBrowserProperties() {
        return browserProperties;
    }

    public void setBrowserProperties(BrowserProperties browserProperties) {
        this.browserProperties = browserProperties;
    }
}
