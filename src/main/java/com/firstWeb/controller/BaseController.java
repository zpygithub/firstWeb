package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zpy on 2017/5/15.
 */
public class BaseController {
    public Token getToken(HttpServletRequest request) {
        Token token = null;
        Object tokenObj = request.getAttribute("Attr_Token");
        if (null != tokenObj) {
            token = (Token) tokenObj;
        }
        return token;
    }
}
