package com.firstWeb.service;

import com.firstWeb.bean.model.User;
import com.firstWeb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

/**
 * Created by zpy on 2017/2/13.
 */
@Service
@EnableTransactionManagement
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private static final String AAA = "<.*?>";

    public User selectUserById(String id) {
        User user = userMapper.selectUserById(id);
        return user;
    }

    public List<User> selectUserByConditions(String value) {
        List<User> user = userMapper.selectUserByConditions(value);
        return user;
    }
}
