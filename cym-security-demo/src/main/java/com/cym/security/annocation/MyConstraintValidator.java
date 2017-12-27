package com.cym.security.annocation;

import com.cym.security.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 0:20
 */

/**
 * 该类可直接使用@Autowired注入bean
 * MyUnique ：定义的注解
 * Object：需验证字段的类型
 */
public class MyConstraintValidator implements ConstraintValidator<MyUnique, Object> {
    @Autowired
    private UserService userService;

    /**
     * 初始化方法
     *
     * @param myUnique 定义的注解
     */
    @Override
    public void initialize(MyUnique myUnique) {

    }

    /**
     *
     * @param o
     * @param constraintValidatorContext
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("====属性值=======" + o);
        int result = userService.getCountName(o);
        return result <= 0;
    }
}
