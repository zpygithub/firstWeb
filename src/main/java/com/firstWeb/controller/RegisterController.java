package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;
import com.firstWeb.bean.param.RegisterParam;
import com.firstWeb.bean.request.LoginReqModel;
import com.firstWeb.bean.request.RegisterReqModel;
import com.firstWeb.service.RegisterService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zpy on 2017/4/8.
 */
@RestController
@RequestMapping({"/register"})
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);

    @RequestMapping(value = "/mainRegister", method = RequestMethod.POST)
    @ResponseBody
    public void mainRegister(RegisterReqModel model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("mainRegister: begin");
        RegisterParam registerParam = new RegisterParam();
        registerParam.setAccount(model.getAccount());
        registerParam.setNickname(model.getNickname());
        registerParam.setPasswd(model.getPasswd());
        registerService.mainRegister(registerParam);
        LOGGER.info("mainRegister: end");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public String list(String model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("mainRegister: begin");
        return model;
    }
}
