package com.firstWeb.service;

import com.firstWeb.bean.param.RegisterParam;
import com.firstWeb.mapper.RegisterMapper;
import com.firstWeb.utils.Md5SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by zpy on 2017/2/13.
 */
@Service
@EnableTransactionManagement
public class RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    /**
     * 注册用户
     *
     * @param registerParam
     */
    public void mainRegister(RegisterParam registerParam) {
        char[] chars = "0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] saltchars = new char[6];
        Random random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(62);
            saltchars[i] = chars[n];
        }
        String salt = new String(saltchars);

        String encryptedPwd = Md5SaltUtil.generate(registerParam.getAccount() + registerParam.getPasswd() + salt);

        registerParam.setPasswd(encryptedPwd);
        registerParam.setSalt(salt);
        registerMapper.mainRegister(registerParam);
    }

   /* *//**
     * 校验账号唯一性
     *
     * @param account
     * @return
     *//*
    public Boolean checkAccount(String account) {
        Boolean a = registerMapper.checkAccount(account);
        if (!registerMapper.checkAccount(account)) {
            return true;
        }
        return false;
    }

    *//**
     * 校验昵称唯一性
     *
     * @param nickname
     * @return
     *//*
    public Boolean checkNickname(String nickname) {
        if (!registerMapper.checkNickname(nickname)) {
            return true;
        }
        return false;
    }*/
}
