package com.firstWeb.mapper;

import com.firstWeb.bean.param.RegisterUserParam;
import com.firstWeb.bean.response.RegisterUserInfo;

import java.util.List;

public interface UserMapper {

    List<RegisterUserInfo> getRegisterUserListOnCondition(RegisterUserParam params);

    int getRegisterUserListSize(RegisterUserParam params);

    RegisterUserInfo getRegisterUserById(long id);

    List<RegisterUserInfo> exportRegisterUserList(RegisterUserParam params);

}
