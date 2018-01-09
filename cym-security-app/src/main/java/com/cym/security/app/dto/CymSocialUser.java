package com.cym.security.app.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/7 16:14
 */
public class CymSocialUser extends SocialUser {
    private Integer age;
    private String mobile;

    public CymSocialUser(Integer age,String mobile,String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.age =age;
        this.mobile=mobile;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
