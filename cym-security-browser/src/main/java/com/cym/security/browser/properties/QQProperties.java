package com.cym.security.browser.properties;

import com.cym.security.browser.enums.LoginType;
import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 21:44
 */
@Data
@Configuration
@PropertySource("classpath:/browser.properties")
@ConfigurationProperties(prefix = "qq")
public class QQProperties extends SocialProperties{
    private String providerId = "callback.do";
}
