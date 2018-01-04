package com.cym.security.browser.social.qq.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/3 0:20
 */
@Component
public class QQConnectionSignUp implements ConnectionSignUp{
    @Override
    public String execute(Connection<?> connection) {
        return connection.getDisplayName();
    }
}
