package com.firstWeb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zpy on 2017/4/8.
 */
@RestController
@RequestMapping({"/login"})
public class LoginController {

    @RequestMapping(value = "/mainLogin", method = RequestMethod.GET)
    public String mainLogin(String id, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(id);
        return "login";
    }
}
