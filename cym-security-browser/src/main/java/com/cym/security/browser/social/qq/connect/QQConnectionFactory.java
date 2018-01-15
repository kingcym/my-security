package com.cym.security.browser.social.qq.connect;

import com.cym.security.browser.social.qq.service.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 22:01
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {


    public QQConnectionFactory(String providerId, String appId,String appSecret) {
        super(providerId, new QQServiceProvider(appId,appSecret), new QQadapter());
    }



}
