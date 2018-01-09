package com.cym.security.app.dto;

import lombok.Data;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/2 23:35
 */
@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;

}
