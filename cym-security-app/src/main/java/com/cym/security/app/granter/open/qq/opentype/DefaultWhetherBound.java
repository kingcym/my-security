package com.cym.security.app.granter.open.qq.opentype;

import com.cym.security.app.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/13 0:35
 */
@Component
public class DefaultWhetherBound implements WhetherBound {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    LoginMapper loginMapper;
    @Override
    public boolean whetherBound(String providerId, String providerUserId) {

        return loginMapper.isWhetherBound(providerId,providerUserId) == 0;
    }
}
