package com.cym.security.sevice;

import com.cym.security.dto.User;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/26 23:31
 */
public interface UserService {
    int insertUser(User user);

    int getCountName(Object o);
}
