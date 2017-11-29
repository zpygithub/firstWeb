package com.firstWeb.controller;

import com.firstWeb.bean.model.Token;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.util.ParamValidateUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    private static final int MINCURRENTPAGE = 1;

    private static final int MAXNUMFORSEARCH = 100;

    public Token getToken(HttpServletRequest request) {
        Token token = null;
        Object tokenObj = request.getAttribute("Attr_Token");
        if (null != tokenObj) {
            token = (Token) tokenObj;
        }
        return token;
    }

    public PageInfo createPageInfo(String page, String size) {
        PageInfo pageInfo = new PageInfo();
        if ((!StringUtils.isEmpty(page) && ParamValidateUtil.isNumber(page)) && (!StringUtils.isEmpty(size) && ParamValidateUtil.isNumber(size))) {
            int pageInt = Integer.parseInt(page);
            int sizeInt = Integer.parseInt(size);
            pageInfo.setCurrentPage(pageInt);
            pageInfo.setPerPageRecords(sizeInt);
            pageInfo.setFrom(pageInt, sizeInt);
        } else {
            pageInfo.setCurrentPage(MINCURRENTPAGE);
            pageInfo.setPerPageRecords(MAXNUMFORSEARCH);
            pageInfo.setFrom(MINCURRENTPAGE, MAXNUMFORSEARCH);
        }
        return pageInfo;
    }
}
