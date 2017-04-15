package com.firstWeb.service;

import com.firstWeb.mapper.UserMapper;
import com.firstWeb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
        List<User> userArray = new ArrayList<User>();
        for (User newUser : user) {
            String newEmail = newUser.getEmail().replaceAll(AAA, " ");
            if (!StringUtils.isEmpty(newEmail.trim())) {
                newUser.setEmail(newEmail);
                userArray.add(newUser);
            }
            /*System.out.println("--------------");
            System.out.println(newEmail);*/
        }
        return userArray;
    }
}
