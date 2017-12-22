package com.firstWeb.service;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.constant.ExportEnum;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.mapper.SystemMapper;
import com.firstWeb.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

    private static final Logger LOGGER = LogManager.getLogger(SystemService.class);

    public List<MainMenu> getMainMenus() {
        List<MainMenu> list = systemMapper.getMainMenus();
        return createMenuTree(list);
    }

    private List<MainMenu> createMenuTree(List<MainMenu> list) {
        List<MainMenu> menus = new ArrayList<MainMenu>();
        if (null != list && list.size() > 0) {
            for (MainMenu mm : list) {
                if (mm.getParentId() == 0) {
                    menus.add(mm);
                    createMenuTree(mm, list);
                }
            }
        }
        return menus;
    }

    private void createMenuTree(MainMenu parent, List<MainMenu> list) {
        for (MainMenu mm : list) {
            if (parent.getId() == (mm.getParentId())) {
                if (null == parent.getChildren()) {
                    parent.setChildren(new ArrayList<MainMenu>());
                }
                parent.getChildren().add(mm);
                createMenuTree(mm, list);
            }
        }
    }

    public CollectionResult<AdministratorInfo> getAdminListOnCondition(AdministratorParam params) {
        CollectionResult<AdministratorInfo> result = new CollectionResult<AdministratorInfo>();
        result.setList(systemMapper.getAdminListOnCondition(params));

        PageInfo pageInfo = params.getPageInfo();
        pageInfo.setTotalRecords(systemMapper.getAdminListSize(params));
        result.setPageInfo(pageInfo);

        return result;
    }

    public String modifyAdminInfo(AdministratorParam param) {
        systemMapper.modifyAdminInfo(param);
        return ResultCode.SUCCESS;
    }

    public AdministratorInfo getAdministratorById(long id) {
        AdministratorInfo administrator = systemMapper.getAdministratorById(id);
        return administrator;
    }

    public Boolean checkOwnNickname(String nickname, String id) {
        String oldNickname = systemMapper.checkOwnNickname(Long.valueOf(id));
        if (nickname.equals(oldNickname)) {
            return true;
        }
        return false;
    }

    public String exportAdminList(AdministratorParam params, ExportTaskParam exportTaskparams) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(ExportEnum.adminList.getValue());
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue(ExportEnum.id.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue(ExportEnum.account.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue(ExportEnum.nickname.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue(ExportEnum.email.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue(ExportEnum.telephone.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue(ExportEnum.createTime.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue(ExportEnum.status.getValue());
        cell.setCellStyle(style);

        List<AdministratorInfo> list = systemMapper.exportAdminList(params);
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            AdministratorInfo adminInfo = list.get(i);
            row.createCell(0).setCellValue(adminInfo.getId());
            row.createCell(1).setCellValue(adminInfo.getAccount());
            row.createCell(2).setCellValue(adminInfo.getNickname());
            row.createCell(3).setCellValue(adminInfo.getEmail());
            row.createCell(4).setCellValue(adminInfo.getTelephone());
            row.createCell(5).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLocalTimeFromUTC(adminInfo.getCreateTime())));
            if (0 == adminInfo.getStatus()) {
                row.createCell(6).setCellValue(ExportEnum.normal.getValue());
            } else {
                row.createCell(6).setCellValue(ExportEnum.freeze.getValue());
            }
        }

//        try {
//            FileOutputStream fos = new FileOutputStream("C:/Users/zwx388880/Desktop/AdministratorInfos.xls");
//            wb.write(fos);
//            fos.close();
//        } catch (Exception e) {
//            LOGGER.error(e);
//        }

        return ResultCode.SUCCESS;
    }

    private boolean excuteDefectTask(TaskInfo exportTaskInfo)
    {
        boolean flag = false;
        try
        {
            List<List<DefectDto>> defectDtos = taskService.excuteQueryDefect(exportTaskInfo);

            List<String> downloadPaths = taskService.excuteExportTask(defectDtos, exportTaskInfo);

            taskService.finishTask(downloadPaths, exportTaskInfo);
            flag = true;
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return flag;
    }
}
