package com.cym.security.dto;

import com.cym.security.annocation.MyUnique;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/26 12:40
 */
@Data
public class User {
    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{};

    @JsonView(UserSimpleView.class)
    @Range(min = 0,max = 100,message = "年龄必须在0~100之间")
    private Integer age;

    @JsonView(UserSimpleView.class)
    @MyUnique(message = "name不能重复")
    private String name;

    @JsonView(UserDetailView.class)
    @Length(min = 8,message = "密码不能少于八位")
    private String password;

}
