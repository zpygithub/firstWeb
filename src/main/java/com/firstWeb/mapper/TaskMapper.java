package com.firstWeb.mapper;

import com.firstWeb.bean.param.ExportTaskParam;

public interface TaskMapper {
    void addExportTask(ExportTaskParam params);

    void updateExportTask(ExportTaskParam params);

    int getRunningTaskQuantities(long creatorId);
}
