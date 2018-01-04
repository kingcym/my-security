package com.cym.security.browser.social.qq.connect;

import com.cym.security.browser.social.qq.service.QQ;
import com.cym.security.browser.social.qq.service.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 21:19
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;

    /**获取Authorization Code 授权码*/
    private static final String URL_AUTHORIZATION = "https://graph.qq.com/oauth2.0/authorize";

    /**通过Authorization Code获取Access Token*/
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    /**
     * Create a new {@link OAuth2ServiceProvider}.
     */
    public QQServiceProvider(String appId,String appSecret) {
        super(new QQOAuth2Template(appId,appSecret,URL_AUTHORIZATION,URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }




}
