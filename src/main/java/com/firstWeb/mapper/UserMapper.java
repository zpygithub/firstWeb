package com.firstWeb.mapper;

import com.firstWeb.model.User;

import java.util.List;

/**
 * Created by zpy on 2017/2/13.
 */
public interface UserMapper {

    User selectUserById(String id);

    List<User> selectUserByConditions(String value);

}
