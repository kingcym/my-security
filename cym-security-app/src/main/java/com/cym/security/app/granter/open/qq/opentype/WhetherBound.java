package com.cym.security.app.granter.open.qq.opentype;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/13 0:30
 */
public interface WhetherBound {
    /**
     * 判断通过第三方登陆的用户是否绑定了
     * @param providerId   第三方类型（qq，微信，微博）
     * @param providerUserId  第三方 openId
     * @return
     */
    boolean whetherBound(String providerId,String providerUserId);
}
