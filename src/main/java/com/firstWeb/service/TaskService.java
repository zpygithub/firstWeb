package com.firstWeb.service;

import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.response.ExportTaskInfo;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.mapper.TaskMapper;
import com.firstWeb.util.DateUtil;
import com.firstWeb.util.ZipUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.security.SecureRandom;
import java.util.List;

@Service
@EnableTransactionManagement
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    private static final Logger LOGGER = LogManager.getLogger(TaskService.class);

    private static final int EXPORTTASKSUCCESS = 1;

    private static final int EXPORTTASKNODATA = 2;

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


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateExportTask(ExportTaskParam params) {
        taskMapper.updateExportTask(params);
    }

    public void updateExportTask(List<String> downloadPaths, ExportTaskParam params) {
        try {
            if (!downloadPaths.isEmpty()) {
                String downloadURL = this.encryptCompressFile(downloadPaths, params);
                params.setDownloadURL(downloadURL);
                params.setStatus(EXPORTTASKSUCCESS);
            } else {
                params.setStatus(EXPORTTASKNODATA);
            }
            this.updateExportTask(params);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /**
     * 加密压缩
     *
     * @param downloadPaths
     * @param params
     * @return
     * @throws Exception
     */
    private String encryptCompressFile(List<String> downloadPaths, ExportTaskParam params) throws Exception {
//        String dest_file_name = params.getFileName();
//        String relativePath = "temp" + File.separator + getDateStoragePath()
//                + (new StringBuffer(UUID.randomUUID().toString())) + File.separator + dest_file_name;
//        String compressPath = ATTACHMENT_STORAGE_PATH + relativePath;
//
//        File dest = FileUtils.getFile(compressPath);
//        if (null != dest) {
//            if (null != dest.getParentFile() && !dest.getParentFile().exists()) {
//                if (!dest.getParentFile().mkdirs()) {
//                    throw new Exception("mkdirs error.");
//                }
//            }
//        }
//
//        ZipUtil zc = new ZipUtil(compressPath);
//        zc.encryptCompress(downloadPaths, compressPath, params.getRemark());
//
//        for (String path : downloadPaths) {
//            FileUtil.deletefile(new File(PathUtils.FilePathFormat(path)).getParentFile().getPath());
//        }
//
//        return relativePath;
        return null;
    }

}
