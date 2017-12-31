package com.firstWeb.service;

import com.firstWeb.bean.param.RegisterParam;
import com.firstWeb.mapper.RegisterMapper;
import com.firstWeb.util.Md5SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void mainRegister(RegisterParam registerParam) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //生成随机salt
        char[] chars = "0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] saltchars = new char[6];
        Random random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int n = random.nextInt(62);
            saltchars[i] = chars[n];
        }
        String salt = new String(saltchars);

        String encryptedPwd = Md5SaltUtil.getEncryptedPasswd(registerParam.getAccount() + registerParam.getPasswd() + salt);

        registerParam.setPasswd(encryptedPwd);
        registerParam.setSalt(salt);
        registerMapper.mainRegister(registerParam);
    }

    /**
     * 校验账号唯一性
     *
     * @param account
     * @return
     */
    public Boolean checkAccount(String account) {
        if (registerMapper.checkAccount(account)) {
            return false;
        }
        return true;
    }

    /**
     * 校验用户名唯一性
     *
     * @param username
     * @return
     */
    public Boolean checkUsername(String username) {
        if (registerMapper.checkUsername(username)) {
            return false;
        }
        return true;
    }

}
