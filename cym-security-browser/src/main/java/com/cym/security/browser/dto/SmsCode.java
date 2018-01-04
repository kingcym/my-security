package com.cym.security.browser.dto;


import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/29 14:51
 */
public class SmsCode {

    //验证码code
    private String mobile;

    //验证码code
    private String code;

    //过期时间
    private LocalDateTime expire;

    public SmsCode( String code, LocalDateTime expire) {
        this.code = code;
        this.expire = expire;
    }

    public SmsCode( String code, int expireIn) {
        this.code = code;
        this.expire = LocalDateTime.now().plusSeconds(expireIn);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public void setExpire(LocalDateTime expire) {
        this.expire = expire;
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expire);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
