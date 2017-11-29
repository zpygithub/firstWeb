package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;
import com.firstWeb.bean.request.LoginReqModel;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.service.LoginService;
import com.firstWeb.util.BasicDateValidateUtil;
import com.firstWeb.util.Md5SaltUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping({"/login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private static final String ACCOUNTISLOCKED = "-1";

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
    public ResultEntity<Token> mainLogin(LoginReqModel model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, CommonException {
        LOGGER.info("mainLogin: begin");
        ResultEntity<Token> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);

        BasicDateValidateUtil.validateIsEmpty(model.getAccount());

        AdministratorInfo administrator = loginService.getAdministratorByAccount(model.getAccount());
        if (null == administrator) {
            result.setCode(ResultCode.ACCOUNTISNOTEXISTENCE);
            return result;
        }
        if (ACCOUNTISLOCKED.equals(administrator.getStatus())) {
            result.setCode(ResultCode.ACCOUNTISLOCKED);
            return result;
        }

        String salt = loginService.getSaltByAccount(model.getAccount());
        String passwdInDb = loginService.getPasswdByAccount(model.getAccount());
        if (Md5SaltUtil.validPasswd(model.getAccount() + model.getPasswd() + salt, passwdInDb)) {
            result.setCode(ResultCode.SUCCESS);
        }

        LOGGER.info("mainLogin: end");
        return result;
    }
}
