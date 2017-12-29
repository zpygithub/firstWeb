package com.firstWeb.service;

import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.response.ExportTaskInfo;
import com.firstWeb.constant.ExportStatusEnum;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.mapper.TaskMapper;
import com.firstWeb.util.DateUtil;
import com.firstWeb.util.PropertiesUtil;
import com.firstWeb.util.ZipUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    private static final Logger LOGGER = LogManager.getLogger(TaskService.class);

    private static final String ZIP = ".zip";

    private static String EXPORTFILEPATH = PropertiesUtil.getValue("export.file.path");

    public ExportTaskParam addExportTask(ExportTaskParam params) throws CommonException {
        if (ExportStatusEnum.EXPORTISRUNNING.getValue() != this.getRunningTaskQuantities(params.getCreatorId())) {
            throw new CommonException(ResultCode.EXPORTTASKISRUNNING, "export task is running");
        }
        params.setFileName(params.getFileName() + ZIP);
        params.setCreateTime(DateUtil.getUTCDate());
        params.setStatus(ExportStatusEnum.EXPORTISRUNNING.getValue());
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateExportTask(ExportTaskParam params) {
        taskMapper.updateExportTask(params);
    }

    public void updateExportTask(String filePath, ExportTaskParam params) {
        try {
            String downloadUrl = this.encryptCompressFile(filePath, params);
            params.setDownloadUrl(downloadUrl);
            params.setStatus(ExportStatusEnum.SUCCESS.getValue());
            this.updateExportTask(params);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    public ExportTaskInfo getExportTaskInfo(long id) {
        ExportTaskInfo exportTaskInfo = taskMapper.getExportTaskInfo(id);
        return exportTaskInfo;
    }

    /**
     * 加密压缩
     *
     * @param filePath
     * @param params
     * @return
     * @throws Exception
     */
    private String encryptCompressFile(String filePath, ExportTaskParam params) throws Exception {
        String fileName = params.getFileName();
        String compressPath = EXPORTFILEPATH + fileName;
        ZipUtil zipUtil = new ZipUtil(compressPath);
        zipUtil.encryptCompress(filePath, compressPath, params.getRemark());
        File file = new File(filePath);
        file.delete();
        return compressPath;
    }

}
