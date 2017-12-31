package com.firstWeb.mapper;

import com.firstWeb.bean.response.RegisterUserInfo;

import java.util.List;

public interface UserMapper {

    RegisterUserInfo selectUserById(String id);

    List<RegisterUserInfo> selectUserByConditions(String value);

}
