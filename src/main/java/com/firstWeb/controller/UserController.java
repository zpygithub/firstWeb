package com.firstWeb.controller;

import com.firstWeb.bean.param.RegisterUserParam;
import com.firstWeb.bean.request.RegisterUserReqModel;
import com.firstWeb.bean.response.RegisterUserInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.common.ResultEntity;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.service.UserService;
import com.firstWeb.util.ParamValidateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping({"/user"})
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);

    /**
     * 获取注册用户列表
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/getRegisterUserListOnCondition")
    public ResultEntity<CollectionResult<RegisterUserInfo>> getRegisterUserListOnCondition(RegisterUserReqModel model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("getRegisterUserListOnCondition: begin");
        ResultEntity<CollectionResult<RegisterUserInfo>> result = new ResultEntity<>();
        result.setCode(ResultCode.FAIL);
//        Token token = getToken(request);
        RegisterUserParam params = new RegisterUserParam();
        params.setAccount(ParamValidateUtil.escapeSpecialCharacter(model.getAccount()));
        params.setUsername(ParamValidateUtil.escapeSpecialCharacter(model.getUsername()));
        params.setEmail(ParamValidateUtil.escapeSpecialCharacter(model.getEmail()));
        params.setTelephone(ParamValidateUtil.escapeSpecialCharacter(model.getTelephone()));
        params.setAddress(ParamValidateUtil.escapeSpecialCharacter(model.getAddress()));
        params.setCreateTimeBegin(model.getCreateTimeBegin());
        params.setCreateTimeEnd(model.getCreateTimeEnd());
        params.setProvince(model.getProvince());
        params.setCity(model.getCity());
        params.setDistrict(model.getDistrict());
        params.setSex(model.getSex());
        params.setStatus(model.getStatus());
        params.setPageInfo(createPageInfo(model.getPage(), model.getSize()));
        CollectionResult<RegisterUserInfo> list = userService.getRegisterUserListOnCondition(params);
        result.setValue(list);
        result.setCode(ResultCode.SUCCESS);
        LOGGER.info("getRegisterUserListOnCondition: end");
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
