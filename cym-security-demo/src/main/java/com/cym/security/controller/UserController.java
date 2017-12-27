package com.cym.security.controller;

import com.cym.security.dto.User;
import com.cym.security.sevice.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Kingcym
 * @Description: @JsonView注解使用  @valid
 * @Date: 2017/12/26 12:39
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @JsonView(User.UserSimpleView.class)
    public List<User> queryUser(User user) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ArrayList<User> list = new ArrayList<>();
        User user1 = new User();
        user1.setAge(11);
        user1.setName("张三");
        user1.setPassword("123456789");
        list.add(user1);
        return list;
    }

    /**
     * {id:\\d+}  "\\d+"正则表达式表示id为数字
     * @param id
     * @return
     */
    @GetMapping("/user/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User queryUserTwo(@PathVariable Integer id) throws Exception {
        User user = new User();
        user.setAge(11);
        user.setName("张三");
        user.setPassword("123456789");
        return user;
    }

    @PostMapping("/user")
    public int addUser(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String err = fieldError.getField() + " : " + fieldError.getDefaultMessage();
                System.err.println(err);
            });
            return 0;
        }
        int result = userService.insertUser(user);
        return result;
    }


}
