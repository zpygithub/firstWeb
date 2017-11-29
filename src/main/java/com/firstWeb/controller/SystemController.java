package com.firstWeb.controller;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.request.AdministratorReqModel;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.common.CollectionResult;
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
    @GetMapping(value = "/getAdminListOnCondition")
    public ResultEntity<CollectionResult<AdministratorInfo>> getAdminListOnCondition(AdministratorReqModel model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("getAdminListOnCondition: begin");
        ResultEntity<CollectionResult<AdministratorInfo>> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);
        AdministratorParam params = new AdministratorParam();
        params.setAccount(model.getAccount());
        params.setNickname(model.getNickname());
        params.setEmail(model.getEmail());
        params.setTelephone(model.getTelephone());
        params.setPageInfo(createPageInfo(model.getPage(), model.getSize()));
        CollectionResult<AdministratorInfo> list = systemService.getAdminListOnCondition(params);
        result.setValue(list);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("getAdminListOnCondition: end");
        return result;
    }

    /**
     * 修改管理员
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/modifyAdminInfo")
    public ResultEntity<String> modifyAdminInfo(AdministratorReqModel model, HttpServletRequest request, HttpServletResponse response) throws CommonException {
        LOGGER.info("modifyAdminInfo: begin");
        ResultEntity<String> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);

        BasicDateValidateUtil.validateIsEmpty(model.getNickname());
        BasicDateValidateUtil.validateIsNickname(model.getNickname());

        if (!registerService.checkNickname(model.getNickname()) && !systemService.checkOwnNickname(model.getNickname(), model.getId())) {
            result.setCode(ResultCode.NICKNAMEISEXISTENCE);
            return result;
        }

        AdministratorParam param = new AdministratorParam();
        param.setId(model.getId());
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
    @GetMapping(value = "/getAdministratorById/{id}")
    public ResultEntity<AdministratorInfo> getAdministratorById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws CommonException {
        LOGGER.info("getAdministratorById: begin");
        ResultEntity<AdministratorInfo> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
        AdministratorInfo administrator = systemService.getAdministratorById(Long.valueOf(id));
        if (null == administrator) {
            throw new CommonException(ResultCode.ACCOUNTISNOTEXISTENCE, "the administrator not exist.");
        }
        result.setValue(administrator);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("getAdministratorById: end");
        return result;
    }

}
