package com.cym.security.annocation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 0:15
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//指定校验逻辑在哪个类中
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyUnique {
    String message() default "{参数错误}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
