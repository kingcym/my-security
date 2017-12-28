package com.cym.security.browser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 13:12
 */
@RestController
public class LoginController {


    @GetMapping("/hello")
    public String hello (){
        return "hello world====";
    }
}
