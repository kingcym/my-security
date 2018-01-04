package com.cym.security.browser.service.sms;

import com.cym.security.browser.dto.SmsCode;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/31 0:55
 */
public interface SmsCodeSenderService {
    //发送手机验证码
    void sendSmsCode(SmsCode smsCode);
}
