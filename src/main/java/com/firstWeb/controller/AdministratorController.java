package com.firstWeb.controller;

import com.firstWeb.bean.model.Administrator;
import com.firstWeb.bean.model.Token;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constants.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zpy on 2017/5/28.
 */
@RestController
@RequestMapping({"/account"})
public class AdministratorController extends BaseController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping(value = "/getAdministratorById")
    public ResultEntity<Administrator> getAdministratorById(HttpServletRequest request, HttpServletResponse response) throws CommonException {
        ResultEntity<Administrator> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
        Token token = getToken(request);
        if (null != token) {
            long id = token.getId();
            Administrator administrator = administratorService.getAdministratorById(id);
            if (null == administrator) {
                throw new CommonException(ResultCode.FAIL, "the administrator not exist.");
            }
            result.setValue(administrator);
            result.setCode(ResultCode.SUCCESS);
        }
        return result;
    }
}
