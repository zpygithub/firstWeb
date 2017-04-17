package com.firstWeb.service;

import com.firstWeb.bean.param.RegisterParam;
import com.firstWeb.common.Md5SaltTool;
import com.firstWeb.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zpy on 2017/2/13.
 */
@Service
@EnableTransactionManagement
public class RegisterService {

    @Autowired
    private RegisterMapper registerMapper;

    public void mainRegister(RegisterParam registerParam) {

        String encryptedPwd = null;
        try {
            encryptedPwd = Md5SaltTool.getEncryptedPwd(registerParam.getPasswd());
            registerParam.setPasswd(encryptedPwd);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        registerMapper.mainRegister(registerParam);
    }
}
