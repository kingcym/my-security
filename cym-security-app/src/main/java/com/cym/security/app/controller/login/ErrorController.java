package com.cym.security.app.controller.login;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/12 22:48
 */
@RestController
public class ErrorController {


    @RequestMapping("/oauth/error")
    public String ss(HttpMessageConverters converters ){
        return "方生错误";
    }
}
