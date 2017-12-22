package com.firstWeb.service;

import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.mapper.TaskMapper;
import com.firstWeb.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.security.SecureRandom;

@Service
@EnableTransactionManagement
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    public ExportTaskParam addExportTask(ExportTaskParam params) throws CommonException {
        if (0 != this.getRunningTaskQuantities(params.getCreatorId())) {
            throw new CommonException(ResultCode.EXPORTTASKISRUNNING, "export task is running");
        }
        params.setFileName(params.getFileName() + ".zip");
        params.setCreateTime(DateUtil.getUTCDate());
        params.setStatus(0);
        params.setRemark(this.getSixRandomPasswd());
        taskMapper.addExportTask(params);
        return params;
    }

    private int getRunningTaskQuantities(long creatorId) {
        int quantities = taskMapper.getRunningTaskQuantities(creatorId);
        return quantities;
    }

    /**
     * 随机6位数字密码
     *
     * @return
     */
    private String getSixRandomPasswd() {
        StringBuilder str = new StringBuilder(10);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        String passwd = str.toString();
        return passwd;
    }

}
