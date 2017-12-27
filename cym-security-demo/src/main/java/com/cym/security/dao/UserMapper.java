package com.cym.security.dao;

import com.cym.security.dto.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/26 23:32
 */
@Mapper
public interface UserMapper {
    int insertUser(User user);

    int getCountName(Object o);
}
