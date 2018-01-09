package com.cym.security.browser.controller.login;

import com.cym.security.browser.dto.SimpleResponse;
import com.cym.security.browser.dto.SocialUserInfo;
import com.cym.security.browser.properties.BrowserProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 21:21
 */
@RestController
public class BrowserSecurityControler {
    private Logger logger = Logger.getLogger(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private BrowserProperties browserProperties;
    @Autowired
    ProviderSignInUtils providerSignInUtils;

    @Autowired(required = false)
    ConnectionRepository connectionRepository;

    @Autowired
    ConnectionFactoryLocator connectionFactoryLocator;
    /**
     * <p>上一个接口，以.html结尾，跳到登录页<p/>
     * <p>上一个接口，不以.html结尾，给个提示<p/>
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)//401状态码
    public SimpleResponse<String> requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //获取哪个url跳转到此接口
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            //上一次请求接口
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:" + targetUrl);
//            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                //跳转
                redirectStrategy.sendRedirect(request, response, browserProperties.getLoginUrl());
//            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

    //
    @RequestMapping("/register")
    public SimpleResponse<String> register(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        return new SimpleResponse<>("====请到绑定页面===");


    }

    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }


    @GetMapping("/session/timeout")
    public void sessionTimeout(HttpServletRequest request,HttpServletResponse response) throws IOException {

        logger.info("===============session已过期,重新登陆=================");

        //跳转
        redirectStrategy.sendRedirect(request, response, browserProperties.getLoginUrl());
    }

    @GetMapping("/myconnect")
    public SimpleResponse connect(NativeWebRequest request, Model model) {

        SimpleResponse simpleResponse = new SimpleResponse();
        Map<String, List<Connection<?>>> connections = connectionRepository.findAllConnections();
        Set<String> strings = connectionFactoryLocator.registeredProviderIds();

//        simpleResponse.setContext(connections);
        simpleResponse.setSss(strings);
        return simpleResponse;
    }
}
