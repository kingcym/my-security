package com.cym.security.app.dto;

import lombok.Data;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 21:25
 */
@Data
public class SimpleResponse<T> {
    private T context;

    private Object sss;

    public SimpleResponse() {

    }

    public SimpleResponse(T context) {
        this.context = context;
    }
}
