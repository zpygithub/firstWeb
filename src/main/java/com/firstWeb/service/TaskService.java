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


    @Transactional(propagation = Propagation.REQUIRED)
    public String updateTask(TaskInfo taskInfo)
    {
        taskMapper.updateTask(taskInfo);
        return ResultCode.Success;
    }

    public void finishTask(List<String> downloadPaths, TaskInfo taskInfo)
    {
        try
        {
            if (!downloadPaths.isEmpty())
            {
                String downloadURL = "";
                if (taskInfo.getTaskName().equals(EXPORT_MEMBER_INFO))
                {
                    downloadURL = this.encryptCompressFile(downloadPaths, taskInfo);
                }
                else
                {
                    downloadURL = this.compressFile(downloadPaths, taskInfo.getFileName());
                }

                taskInfo.setDownloadURL(downloadURL);
                taskInfo.setStatus(TASK_SUCCESS_STATUS);
            }
            else
            {
                taskInfo.setStatus(TASK_NOT_EXPORT_FILE_STATUS);
            }
            this.updateTask(taskInfo);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    /**
     * 加密压缩
     * @param pathNames
     */
    private String encryptCompressFile(List<String> downloadPaths, TaskInfo taskInfo)
            throws Exception
    {
        String dest_file_name = taskInfo.getFileName();
        String relativePath = "temp" + File.separator + getDateStoragePath()
                + (new StringBuffer(UUID.randomUUID().toString())) + File.separator + dest_file_name;
        String compressPath = ATTACHMENT_STORAGE_PATH + relativePath;

        File dest = FileUtils.getFile(compressPath);
        if (null != dest)
        {
            if (null != dest.getParentFile() && !dest.getParentFile().exists())
            {
                if (!dest.getParentFile().mkdirs())
                {
                    throw new Exception("mkdirs error.");
                }
            }
        }

        ZipUtils zc = new ZipUtils(compressPath);
        zc.encryptCompress(downloadPaths, compressPath, taskInfo.getRemark());

        for (String path : downloadPaths)
        {
            FileUtil.deletefile(new File(PathUtils.FilePathFormat(path)).getParentFile().getPath());
        }

        return relativePath;
    }

}
