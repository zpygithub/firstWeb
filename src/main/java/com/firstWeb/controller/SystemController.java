package com.firstWeb.controller;

import com.firstWeb.bean.model.Administrator;
import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.param.RegisterParam;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.service.RegisterService;
import com.firstWeb.service.SystemService;
import com.firstWeb.util.BasicDateValidateUtil;
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

    @Autowired
    private RegisterService registerService;

    private static final Logger LOGGER = LogManager.getLogger(SystemController.class);

    /**
     * 获取菜单
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getMainMenus")
    public ResultEntity<List<MainMenu>> getMainMenus(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("getMainMenus: begin");
        ResultEntity<List<MainMenu>> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);
        List<MainMenu> list = systemService.getMainMenus();
        result.setValue(list);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("getMainMenus: end");
        return result;
    }

    /**
     * 获取管理员列表
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getAdminList")
    public ResultEntity<List<Administrator>> getAdminList(Administrator model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("getAdminList: begin");
        ResultEntity<List<Administrator>> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);
        AdministratorParam params = new AdministratorParam();
        params.setAccount(model.getAccount());
        params.setNickname(model.getNickname());
        params.setEmail(model.getEmail());
        params.setTelephone(model.getTelephone());
        List<Administrator> list = systemService.getAdminList(params);
        result.setValue(list);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("getAdminList: end");
        return result;
    }

    /**
     * 修改管理员
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/modifyAdminInfo")
    public ResultEntity<String> modifyAdminInfo(Administrator model, HttpServletRequest request, HttpServletResponse response) throws CommonException {
        LOGGER.info("modifyAdminInfo: begin");
        ResultEntity<String> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);

        BasicDateValidateUtil.validateIsEmpty(model.getNickname());
        BasicDateValidateUtil.validateIsNickname(model.getNickname());

        if (!registerService.checkNickname(model.getNickname())) {
            result.setCode(ResultCode.NICKNAMEISEXISTENCE);
            return result;
        }

        AdministratorParam param = new AdministratorParam();
        param.setNickname(model.getNickname());
        param.setEmail(model.getEmail());
        param.setTelephone(model.getTelephone());
        systemService.modifyAdminInfo(param);

        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("modifyAdminInfo: end");
        return result;
    }

    /**
     * 获取管理员信息
     *
     * @param request
     * @param response
     * @return
     * @throws CommonException
     */
    @GetMapping(value = "/getAdministratorById")
    public ResultEntity<Administrator> getAdministratorById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws CommonException {
        LOGGER.info("getAdministratorById: begin");
        ResultEntity<Administrator> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
        Administrator administrator = systemService.getAdministratorById(Long.valueOf(id));
        if (null == administrator) {
            throw new CommonException(ResultCode.ACCOUNTISNOTEXISTENCE, "the administrator not exist.");
        }
        result.setValue(administrator);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("getAdministratorById: end");
        return result;
    }

}
