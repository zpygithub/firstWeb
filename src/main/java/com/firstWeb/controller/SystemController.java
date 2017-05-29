package com.firstWeb.controller;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.model.Token;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zpy on 2017/5/15.
 */
@RestController
@RequestMapping({"/system"})
public class SystemController extends BaseController {

    @Autowired
    private SystemService systemService;

    private static final Logger LOGGER = LogManager.getLogger(SystemController.class);

    @GetMapping(value = "/getMainMenus")
    public ResultEntity<List<MainMenu>> getMainMenus(HttpServletRequest request, HttpServletResponse response) {
        ResultEntity<List<MainMenu>> result = new ResultEntity<>();
//        Token token = getToken(request);
        List<MainMenu> list = systemService.getMainMenus();
        result.setValue(list);
        return result;
    }
}
