package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;
import com.firstWeb.bean.request.LoginReqModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
@RequestMapping({"/login"})
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    /*@RequestMapping(value = "/mainLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultEntity<Token> mainLogin(LoginReqModel model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("mainLogin: begin");
        String account = model.getAccount();
        return null;
    }*/
}
