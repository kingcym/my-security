package com.cym.security.browser.social.qq.connect;

import com.cym.security.browser.social.qq.dto.QQUserInfo;
import com.cym.security.browser.social.qq.service.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 21:47
 */
public class QQadapter implements ApiAdapter<QQ> {

    /**
     * 测试api是否可用
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUser();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null); //个人主页
        values.setProviderUserId(userInfo.getOpenId());

    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    /**
     * 特定 第三方  才有   qq没有
     * @param api
     * @param message
     */
    @Override
    public void updateStatus(QQ api, String message) {

    }
}
