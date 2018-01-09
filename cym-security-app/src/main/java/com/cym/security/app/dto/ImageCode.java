package com.cym.security.app.dto;


import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/29 14:51
 */
public class ImageCode {

    //验证码图片
    private BufferedImage image;

    //验证码code
    private String code;

    //过期时间
    private LocalDateTime expire;

    public ImageCode(BufferedImage image, String code, LocalDateTime expire) {
        this.image = image;
        this.code = code;
        this.expire = expire;
    }

    public ImageCode(BufferedImage image, String code, int expireIn) {
        this.image = image;
        this.code = code;
        this.expire = LocalDateTime.now().plusSeconds(expireIn);
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
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
}
