package com.firstWeb.controller;

import com.firstWeb.bean.model.ExportTask;
import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.request.AdministratorReqModel;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.bean.response.ExportTaskInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constant.ExportEnum;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.service.RegisterService;
import com.firstWeb.service.SystemService;
import com.firstWeb.service.TaskService;
import com.firstWeb.util.BasicDateValidateUtil;
import com.firstWeb.util.ParamValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping({"/system"})
public class SystemController extends BaseController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private TaskService taskService;

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
        params.setAccount(ParamValidateUtil.escapeSpecialCharacter(model.getAccount()));
        params.setUsername(ParamValidateUtil.escapeSpecialCharacter(model.getUsername()));
        params.setEmail(ParamValidateUtil.escapeSpecialCharacter(model.getEmail()));
        params.setTelephone(ParamValidateUtil.escapeSpecialCharacter(model.getTelephone()));
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

        BasicDateValidateUtil.validateIsEmpty(model.getUsername());
        BasicDateValidateUtil.validateIsUsername(model.getUsername());

        if (!registerService.checkUsername(model.getUsername()) && !systemService.checkOwnUsername(model.getUsername(), model.getId())) {
            result.setCode(ResultCode.USERNAMEISEXISTENCE);
            return result;
        }

        AdministratorParam param = new AdministratorParam();
        param.setId(Long.valueOf(model.getId()));
        param.setUsername(model.getUsername());
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

    /**
     * 获取管理员信息
     *
     * @param request
     * @param response
     * @return
     * @throws CommonException
     */
    @GetMapping(value = "/getAdministrator")
    public ResultEntity<AdministratorInfo> getAdministrator(HttpServletRequest request, HttpServletResponse response) throws CommonException {
        LOGGER.info("getAdministrator: begin");
        ResultEntity<AdministratorInfo> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);
//        if (null != token) {
//            long id = token.getId();
        AdministratorInfo administrator = systemService.getAdministratorById(1);
        if (null == administrator) {
            throw new CommonException(ResultCode.ACCOUNTISNOTEXISTENCE, "the administrator not exist.");
        }
        result.setValue(administrator);
        result.setCode(ResultCode.SUCCESS);
//        }
        LOGGER.info("getAdministrator: end");
        return result;
    }

    /**
     * 导出管理员列表
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/exportAdminList")
    public ResultEntity<ExportTaskInfo> exportAdminList(AdministratorReqModel model, HttpServletRequest request, HttpServletResponse response) throws IOException, CommonException {
        LOGGER.info("exportAdminList: begin");
        ResultEntity<ExportTaskInfo> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);
//        long id = token.getId();
        ExportTaskParam exportTaskparams = new ExportTaskParam();
        exportTaskparams.setCreatorId(1);

        AdministratorParam params = new AdministratorParam();
        params.setAccount(ParamValidateUtil.escapeSpecialCharacter(model.getAccount()));
        params.setUsername(ParamValidateUtil.escapeSpecialCharacter(model.getUsername()));
        params.setEmail(ParamValidateUtil.escapeSpecialCharacter(model.getEmail()));
        params.setTelephone(ParamValidateUtil.escapeSpecialCharacter(model.getTelephone()));
        ExportTaskInfo exportTaskInfo = systemService.exportAdminList(params, exportTaskparams);

        result.setValue(exportTaskInfo);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("exportAdminList: end");
        return result;
    }

    /**
     * 下载
     *
     * @param downloadUrl
     * @param request
     * @param response
     */
    @PostMapping(value = "/downLoadToPage")
    public void downLoadToPage(String downloadUrl, HttpServletRequest request, HttpServletResponse response) {
        downloadToPage(downloadUrl, response);
    }

}
