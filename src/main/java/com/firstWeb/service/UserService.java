package com.firstWeb.service;

import com.firstWeb.bean.response.UserInfo;
import com.firstWeb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@Service
@EnableTransactionManagement
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private static final String AAA = "<.*?>";

    public UserInfo selectUserById(String id) {
        UserInfo user = userMapper.selectUserById(id);
        return user;
    }

    public List<UserInfo> selectUserByConditions(String value) {
        List<UserInfo> user = userMapper.selectUserByConditions(value);
        return user;
    }
}
