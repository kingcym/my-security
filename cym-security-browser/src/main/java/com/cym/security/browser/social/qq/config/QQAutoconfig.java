package com.cym.security.browser.social.qq.config;

import com.alibaba.druid.sql.visitor.functions.Locate;
import com.cym.security.browser.properties.QQProperties;
import com.cym.security.browser.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.social.connect.ConnectionFactory;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 22:51
 */
@Configuration
//@ConditionalOnProperty(prefix = "qq",name = "appId")
public class QQAutoconfig extends SocialAutoConfigurerAdapter {
    @Autowired
    private QQProperties qqProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret());
    }
}
