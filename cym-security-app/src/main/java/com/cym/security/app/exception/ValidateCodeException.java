package com.cym.security.app.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/29 22:15
 */
public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -5509263821536201978L;

    private int code;

    public ValidateCodeException(int code,String msg) {
        super(msg);
        this.code = code;
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }

    public int getCode() {
        return code;
    }
}
