package com.firstWeb.service;

import com.firstWeb.bean.param.RegisterUserParam;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.bean.response.RegisterUserInfo;
import com.firstWeb.common.CollectionResult;
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

    public CollectionResult<RegisterUserInfo> getRegisterUserListOnCondition(RegisterUserParam params) {
        CollectionResult<RegisterUserInfo> result = new CollectionResult<>();
        result.setList(userMapper.getRegisterUserListOnCondition(params));

        PageInfo pageInfo = params.getPageInfo();
        pageInfo.setTotalRecords(userMapper.getRegisterUserListSize(params));
        result.setPageInfo(pageInfo);

        return result;
    }

}
