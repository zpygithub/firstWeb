package com.firstWeb.service;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.bean.response.ExportTaskInfo;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.constant.AdminStatusEnum;
import com.firstWeb.constant.ExcelRowEnum;
import com.firstWeb.constant.ExportEnum;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;
import com.firstWeb.mapper.SystemMapper;
import com.firstWeb.util.DateUtil;
import com.firstWeb.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
public class SystemService {
    @Autowired
    private TaskService taskService;

    @Autowired
    private SystemMapper systemMapper;

    private static final Logger LOGGER = LogManager.getLogger(SystemService.class);

    private static final String EXPORTADMINLIST = "export_adminList";

    private static final String ADMINLIST = "AdminList";

    private static final String XLS = ".xls";

    private static String EXPORTFILEPATH = PropertiesUtil.getValue("export.file.path");

    public List<MainMenu> getMainMenus() {
        List<MainMenu> list = systemMapper.getMainMenus();
        return createMenuTree(list);
    }

    private List<MainMenu> createMenuTree(List<MainMenu> list) {
        List<MainMenu> menus = new ArrayList<>();
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
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(mm);
                createMenuTree(mm, list);
            }
        }
    }

    public CollectionResult<AdministratorInfo> getAdminListOnCondition(AdministratorParam params) {
        CollectionResult<AdministratorInfo> result = new CollectionResult<>();
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

    public ExportTaskInfo exportAdminList(AdministratorParam params, ExportTaskParam exportTaskparams) throws IOException, CommonException {
        exportTaskparams.setTaskName(ExportEnum.EXPORT.getValue() + ExportEnum.ADMINLIST.getValue());
        exportTaskparams.setFileName(EXPORTADMINLIST);
        exportTaskparams = taskService.addExportTask(exportTaskparams);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(ExportEnum.ADMINLIST.getValue());
        HSSFRow row = sheet.createRow(ExcelRowEnum.FIRSTROW.getValue());
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

        HSSFCell cell;
        cell = row.createCell(ExcelRowEnum.FIRSTROW.getValue());
        cell.setCellValue(ExportEnum.ID.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(ExcelRowEnum.SECONDROW.getValue());
        cell.setCellValue(ExportEnum.ACCOUNT.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(ExcelRowEnum.THIRDROW.getValue());
        cell.setCellValue(ExportEnum.NICKNAME.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(ExcelRowEnum.FOURTHROW.getValue());
        cell.setCellValue(ExportEnum.EMAIL.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(ExcelRowEnum.FIFTHROW.getValue());
        cell.setCellValue(ExportEnum.TELEPHONE.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(ExcelRowEnum.SIXTHROW.getValue());
        cell.setCellValue(ExportEnum.CREATETIME.getValue());
        cell.setCellStyle(style);
        cell = row.createCell(ExcelRowEnum.SeventhROW.getValue());
        cell.setCellValue(ExportEnum.STATUS.getValue());
        cell.setCellStyle(style);

        List<AdministratorInfo> list = systemMapper.exportAdminList(params);
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            AdministratorInfo adminInfo = list.get(i);
            row.createCell(ExcelRowEnum.FIRSTROW.getValue()).setCellValue(adminInfo.getId());
            row.createCell(ExcelRowEnum.SECONDROW.getValue()).setCellValue(adminInfo.getAccount());
            row.createCell(ExcelRowEnum.THIRDROW.getValue()).setCellValue(adminInfo.getNickname());
            row.createCell(ExcelRowEnum.FOURTHROW.getValue()).setCellValue(adminInfo.getEmail());
            row.createCell(ExcelRowEnum.FIFTHROW.getValue()).setCellValue(adminInfo.getTelephone());
            row.createCell(ExcelRowEnum.SIXTHROW.getValue()).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLocalTimeFromUTC(adminInfo.getCreateTime())));
            if (AdminStatusEnum.NORMAL.getValue() == adminInfo.getStatus()) {
                row.createCell(ExcelRowEnum.SeventhROW.getValue()).setCellValue(ExportEnum.NORMAL.getValue());
            } else {
                row.createCell(ExcelRowEnum.SeventhROW.getValue()).setCellValue(ExportEnum.FREEZE.getValue());
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(EXPORTFILEPATH + ADMINLIST + XLS);
            wb.write(fos);
            fos.close();
        } catch (Exception e) {
            LOGGER.error(e);
        }
        taskService.updateExportTask(EXPORTFILEPATH + ADMINLIST + XLS, exportTaskparams);
        ExportTaskInfo exportTaskInfo = taskService.getExportTaskInfo(exportTaskparams.getId());
        return exportTaskInfo;
    }

}
