package com.cym.security.browser.dto;

import lombok.Data;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 21:25
 */
@Data
public class SimpleResponse<T> {
    private T context;

    public SimpleResponse(T context) {
        this.context = context;
    }
}
