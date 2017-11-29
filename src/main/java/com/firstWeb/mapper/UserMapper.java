package com.firstWeb.mapper;

import com.firstWeb.bean.response.UserInfo;

import java.util.List;

public interface UserMapper {

    UserInfo selectUserById(String id);

    List<UserInfo> selectUserByConditions(String value);

}
