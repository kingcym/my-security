package com.cym.security.app.controller.login;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 21:21
 */
@RestController
public class AppSecurityControler {
    private Logger logger = Logger.getLogger(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @RequestMapping(value = "/authentication/require", method= RequestMethod.POST)
    public void postAccessToken(Principal principal, Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {

        System.out.println();
    }


}
