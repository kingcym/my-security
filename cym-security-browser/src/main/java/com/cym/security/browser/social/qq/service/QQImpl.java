package com.cym.security.browser.social.qq.service;

import com.cym.security.browser.social.qq.dto.QQUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.social.ServiceProvider;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 20:29
 */
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    private Logger logger = Logger.getLogger(getClass());
    /**
     * 获取openId 地址
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取用户信息 地址
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        /**
         * TokenStrategy.ACCESS_TOKEN_PARAMETER的解释
         * Indicates that the access token should be carried as a query parameter named "access_token".
         */
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        /**获取openId*/
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = super.getRestTemplate().getForObject(url, String.class);
        logger.info("======QQ请求openId返回信息===========：" + result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");

    }

    /**
     * 获取用户信息
     */
    @Override
    public QQUserInfo getUser() {

        String url = String.format(URL_GET_USERINFO, appId, openId);
        String userInfo = super.getRestTemplate().getForObject(url, String.class);
        logger.info("======QQ请求获取用户信息===========：" + userInfo);
        QQUserInfo userInfos = null;
        try {
            userInfos = objectMapper.readValue(userInfo, QQUserInfo.class);
            userInfos.setOpenId(openId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfos;

    }
}
