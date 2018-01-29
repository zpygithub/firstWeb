package com.firstWeb.service;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.bean.response.DistrictInfo;
import com.firstWeb.bean.response.ExportTaskInfo;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.constant.*;
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

import static com.firstWeb.util.DateUtil.getLocalTime;

@Service
@EnableTransactionManagement
public class SystemService {
    @Autowired
    private TaskService taskService;

    @Autowired
    private SystemMapper systemMapper;

    private static final Logger LOGGER = LogManager.getLogger(SystemService.class);

    private static String EXPORTFILEPATH = PropertiesUtil.getValue("export.file.path");

    private static final String COMMA = ",";

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

    public String changeAdminStatus(AdministratorParam param) {
        systemMapper.changeAdminStatus(param);
        return ResultCode.SUCCESS;
    }

    public AdministratorInfo getAdministratorById(long id) {
        AdministratorInfo administrator = systemMapper.getAdministratorById(id);
        return administrator;
    }

    public Boolean checkOwnUsername(String username, String id) {
        String oldUsername = systemMapper.checkOwnUsername(Long.valueOf(id));
        if (username.equals(oldUsername)) {
            return true;
        }
        return false;
    }

    public ExportTaskInfo exportAdminList(AdministratorParam params, ExportTaskParam exportTaskparams) throws IOException, CommonException {
        exportTaskparams.setTaskName(ExportEnum.EXPORT.getValue() + ExportEnum.ADMINISTRATORLIST.getValue());
        exportTaskparams.setFileName(ExportEnum.EXPORTADMINLIST.getValue() + getLocalTime().replace(ExportEnum.COLONREG.getValue(), "").replace(ExportEnum.HYPHENREG.getValue(), "").replace(ExportEnum.BLANKREG.getValue(), ""));
        exportTaskparams = taskService.addExportTask(exportTaskparams);

        List<AdministratorInfo> list = systemMapper.exportAdminList(params);
        ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
        if (null == list || 0 == list.size()) {
            exportTaskparams.setStatus(ExportStatusEnum.EXPORTNODATA.getValue());
            taskService.updateExportTask(exportTaskparams);
            return exportTaskInfo;
        } else {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(ExportEnum.ADMINISTRATORLIST.getValue());
            HSSFRow row = sheet.createRow(ExcelRowEnum.FIRSTROW.getValue());
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式

            HSSFCell cell;
            cell = row.createCell(ExcelRowEnum.FIRSTROW.getValue());
            cell.setCellValue(ExportEnum.ACCOUNT.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.SECONDROW.getValue());
            cell.setCellValue(ExportEnum.USERNAME.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.THIRDROW.getValue());
            cell.setCellValue(ExportEnum.EMAIL.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.FOURTHROW.getValue());
            cell.setCellValue(ExportEnum.TELEPHONE.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.FIFTHROW.getValue());
            cell.setCellValue(ExportEnum.CREATETIME.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.SIXTHROW.getValue());
            cell.setCellValue(ExportEnum.STATUS.getValue());
            cell.setCellStyle(style);
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                AdministratorInfo adminInfo = list.get(i);
                row.createCell(ExcelRowEnum.FIRSTROW.getValue()).setCellValue(adminInfo.getAccount());
                row.createCell(ExcelRowEnum.SECONDROW.getValue()).setCellValue(adminInfo.getUsername());
                row.createCell(ExcelRowEnum.THIRDROW.getValue()).setCellValue(adminInfo.getEmail());
                row.createCell(ExcelRowEnum.FOURTHROW.getValue()).setCellValue(adminInfo.getTelephone());
                row.createCell(ExcelRowEnum.FIFTHROW.getValue()).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLocalTimeFromUTC(adminInfo.getCreateTime())));
                if ((UserStatusEnum.NORMAL.getValue() + "").equals(adminInfo.getStatus())) {
                    row.createCell(ExcelRowEnum.SIXTHROW.getValue()).setCellValue(ExportEnum.NORMAL.getValue());
                } else {
                    row.createCell(ExcelRowEnum.SIXTHROW.getValue()).setCellValue(ExportEnum.FREEZE.getValue());
                }
            }
            try {
                FileOutputStream fos = new FileOutputStream(EXPORTFILEPATH + ExportEnum.ADMINLIST.getValue() + ExportEnum.XLS.getValue());
                wb.write(fos);
                fos.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
            taskService.updateExportTask(EXPORTFILEPATH + ExportEnum.ADMINLIST.getValue() + ExportEnum.XLS.getValue(), exportTaskparams);
            exportTaskInfo = taskService.getExportTaskInfo(exportTaskparams.getId());
        }
        return exportTaskInfo;
    }

    public List<DistrictInfo> getDistricts(String code) {
        List<DistrictInfo> districtList = new ArrayList<>();
        String[] codeArray = code.split(COMMA);
        for (String codeString : codeArray) {
            districtList.addAll(systemMapper.getDistricts(codeString));
        }
        return districtList;
    }

}
