package com.cym.security.controller;

import com.cym.security.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/26 12:39
 */
@RestController
public class UserController {

    @GetMapping("/user")
    public List<User> queryUser(){
        ArrayList<User> list = new ArrayList<>();
        System.out.println("====================================");
        return list;
    }
}
