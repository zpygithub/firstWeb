package com.firstWeb.service;

import com.firstWeb.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zpy on 2017/2/13.
 */
@Service
@EnableTransactionManagement
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public String getSaltByAccount(String account) {
        String salt = loginMapper.getSaltByAccount(account);
        return salt;
    }

    public String getPasswdByAccount(String account) {
        String passwd = loginMapper.getPasswdByAccount(account);
        return passwd;
    }
}
