package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;
import com.firstWeb.bean.request.LoginReqModel;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constants.ResultCode;
import com.firstWeb.service.LoginService;
import com.firstWeb.utils.Md5SaltUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zpy on 2017/4/8.
 */
@RestController
@RequestMapping({"/login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    /**
     * 用户登录
     *
     * @param model
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/mainLogin")
    @ResponseBody
    public ResultEntity<Token> mainLogin(LoginReqModel model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LOGGER.info("mainLogin: begin");
        ResultEntity<Token> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
        String salt = loginService.getSaltByAccount(model.getAccount());
        String passwdInDb = loginService.getPasswdByAccount(model.getAccount());
        if (Md5SaltUtil.validPasswd(model.getAccount() + model.getPasswd() + salt, passwdInDb)) {
            result.setCode(ResultCode.SUCCESS);
        }

        LOGGER.info("mainLogin: end");
        return result;
    }
}
