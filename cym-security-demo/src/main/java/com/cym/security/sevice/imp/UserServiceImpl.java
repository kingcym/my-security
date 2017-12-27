package com.cym.security.sevice.imp;

import com.cym.security.dao.UserMapper;
import com.cym.security.dto.User;
import com.cym.security.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/26 23:32
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int getCountName(Object o) {
        return userMapper.getCountName(o);
    }
}
