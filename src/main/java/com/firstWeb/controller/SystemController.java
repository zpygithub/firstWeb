package com.firstWeb.controller;

import com.firstWeb.bean.model.ResourceMenu;
import com.firstWeb.bean.model.Token;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zpy on 2017/5/15.
 */
public class SystemController extends BaseController {

    @Autowired
    private SystemService systemService;

    public ResultEntity<List<ResourceMenu>> getResourceMenusById(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) {
        ResultEntity<List<ResourceMenu>> result = new ResultEntity<>();
        Token token = getToken(request);
        List<ResourceMenu> list = null;
        list = systemService.getResourceMenusById(token.getId());
        result.setValue(list);
        return result;
    }
}
