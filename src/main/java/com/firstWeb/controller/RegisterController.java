package com.firstWeb.controller;

import com.firstWeb.bean.param.RegisterParam;
import com.firstWeb.bean.request.RegisterReqModel;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.service.RegisterService;
import com.firstWeb.util.BasicDateValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping({"/register"})
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);

    /**
     * 注册用户
     *
     * @param model
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/mainRegister")
    @ResponseBody
    public ResultEntity<String> mainRegister(RegisterReqModel model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, CommonException {
        LOGGER.info("mainRegister: begin");
        ResultEntity<String> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);

        BasicDateValidateUtil.validateIsEmpty(model.getAccount());
        BasicDateValidateUtil.validateIsEmpty(model.getNickname());
        BasicDateValidateUtil.validateIsEmpty(model.getPasswd());
        BasicDateValidateUtil.validateIsEmpty(model.getConfirmPasswd());

        //账号6-20位、昵称2-8位、密码6-20位
        BasicDateValidateUtil.validateIsAccount(model.getAccount());
        BasicDateValidateUtil.validateIsNickname(model.getNickname());
        BasicDateValidateUtil.validateIsPasswd(model.getPasswd());

        if (!model.getPasswd().equals(model.getConfirmPasswd())) {
            result.setCode(ResultCode.PASSWDCANNOTBRDIFFRENT);
            return result;
        }
        if (!registerService.checkAccount(model.getAccount())) {
            result.setCode(ResultCode.ACCOUNTISEXISTENCE);
            return result;
        }
        if (!registerService.checkNickname(model.getNickname())) {
            result.setCode(ResultCode.NICKNAMEISEXISTENCE);
            return result;
        }

        RegisterParam registerParam = new RegisterParam();
        registerParam.setAccount(model.getAccount());
        registerParam.setNickname(model.getNickname());
        registerParam.setPasswd(model.getPasswd());
        registerService.mainRegister(registerParam);

        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("mainRegister: end");
        return result;
    }


}
