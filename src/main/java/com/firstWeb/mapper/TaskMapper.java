package com.firstWeb.mapper;

import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.response.ExportTaskInfo;

public interface TaskMapper {
    void addExportTask(ExportTaskParam params);

    void updateExportTask(ExportTaskParam params);

    int getRunningTaskQuantities(long creatorId);

    ExportTaskInfo getExportTaskInfo(long id);
}
