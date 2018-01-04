package com.cym.security.browser.properties;

import com.cym.security.browser.enums.LoginType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 21:44
 */
@Data
@Configuration
@PropertySource("classpath:/browser.properties")
@ConfigurationProperties(prefix = "browser")
public class BrowserProperties {
    private String loginUrl = "/html/index.html";

    private String imageUrl;

    private String smsUrl;

    private LoginType loginType = LoginType.JSON;

    //单位秒
    private int rememberMeSeconds = 600;
}
