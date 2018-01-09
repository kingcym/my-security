package com.cym.security.app.mapper;

import com.cym.security.app.dto.MyUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/28 14:21
 */
@Mapper
public interface LoginMapper {
    //查找用户信息
    MyUser getUser(String name);

    //根据电话号码查找
    MyUser getUserByPhone(String mobile);
}
