package com.firstWeb.service;

import com.firstWeb.bean.param.ExportTaskParam;
import com.firstWeb.bean.param.RegisterUserParam;
import com.firstWeb.bean.response.ExportTaskInfo;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.bean.response.RegisterUserInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.constant.*;
import com.firstWeb.exception.CommonException;
import com.firstWeb.mapper.UserMapper;
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
import java.util.List;

import static com.firstWeb.util.DateUtil.getLocalTime;

@Service
@EnableTransactionManagement
public class UserService {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserMapper userMapper;

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private static String EXPORTFILEPATH = PropertiesUtil.getValue("export.file.path");

    private static final String COMMA = ",";

    public CollectionResult<RegisterUserInfo> getRegisterUserListOnCondition(RegisterUserParam params) {
        CollectionResult<RegisterUserInfo> result = new CollectionResult<>();
        result.setList(userMapper.getRegisterUserListOnCondition(params));

        PageInfo pageInfo = params.getPageInfo();
        pageInfo.setTotalRecords(userMapper.getRegisterUserListSize(params));
        result.setPageInfo(pageInfo);

        return result;
    }

    public ExportTaskInfo exportRegisterUserList(RegisterUserParam params, ExportTaskParam exportTaskparams) throws IOException, CommonException {
        exportTaskparams.setTaskName(ExportEnum.EXPORT.getValue() + ExportEnum.REGISTERUSERLIST.getValue());
        exportTaskparams.setFileName(ExportEnum.EXPORTREGISTERUSERLIST.getValue() + getLocalTime().replace(ExportEnum.COLONREG.getValue(), "").replace(ExportEnum.HYPHENREG.getValue(), "").replace(ExportEnum.BLANKREG.getValue(), ""));
        exportTaskparams = taskService.addExportTask(exportTaskparams);

        List<RegisterUserInfo> list = userMapper.exportRegisterUserList(params);
        ExportTaskInfo exportTaskInfo = new ExportTaskInfo();
        if (null == list || 0 == list.size()) {
            exportTaskparams.setStatus(ExportStatusEnum.EXPORTNODATA.getValue());
            taskService.updateExportTask(exportTaskparams);
            return exportTaskInfo;
        } else {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet(ExportEnum.REGISTERUSERLIST.getValue());
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
            cell.setCellValue(ExportEnum.SEX.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.FOURTHROW.getValue());
            cell.setCellValue(ExportEnum.EMAIL.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.FIFTHROW.getValue());
            cell.setCellValue(ExportEnum.TELEPHONE.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.SIXTHROW.getValue());
            cell.setCellValue(ExportEnum.DISTRICT.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.SEVENTHROW.getValue());
            cell.setCellValue(ExportEnum.ADDRESS.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.EIGHTHROW.getValue());
            cell.setCellValue(ExportEnum.CREATETIME.getValue());
            cell.setCellStyle(style);
            cell = row.createCell(ExcelRowEnum.NINTHROW.getValue());
            cell.setCellValue(ExportEnum.STATUS.getValue());
            cell.setCellStyle(style);
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i + 1);
                RegisterUserInfo registerUserInfo = list.get(i);
                row.createCell(ExcelRowEnum.FIRSTROW.getValue()).setCellValue(registerUserInfo.getAccount());
                row.createCell(ExcelRowEnum.SECONDROW.getValue()).setCellValue(registerUserInfo.getUsername());
                if ((UserSexEnum.MALE.getValue() + "").equals(registerUserInfo.getSex())) {
                    row.createCell(ExcelRowEnum.THIRDROW.getValue()).setCellValue(ExportEnum.MALE.getValue());
                } else if ((UserSexEnum.FEMALE.getValue() + "").equals(registerUserInfo.getSex())) {
                    row.createCell(ExcelRowEnum.THIRDROW.getValue()).setCellValue(ExportEnum.FEMALE.getValue());
                } else {
                    row.createCell(ExcelRowEnum.THIRDROW.getValue()).setCellValue(ExportEnum.UNKNOWN.getValue());
                }
                row.createCell(ExcelRowEnum.FOURTHROW.getValue()).setCellValue(registerUserInfo.getEmail());
                row.createCell(ExcelRowEnum.FIFTHROW.getValue()).setCellValue(registerUserInfo.getTelephone());
                row.createCell(ExcelRowEnum.SIXTHROW.getValue()).setCellValue(registerUserInfo.getDistrictInfo().getProvinceName() + registerUserInfo.getDistrictInfo().getCityName() + registerUserInfo.getDistrictInfo().getDistrictName());
                row.createCell(ExcelRowEnum.SEVENTHROW.getValue()).setCellValue(registerUserInfo.getAddress());
                row.createCell(ExcelRowEnum.EIGHTHROW.getValue()).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLocalTimeFromUTC(registerUserInfo.getCreateTime())));
                if ((UserStatusEnum.NORMAL.getValue() + "").equals(registerUserInfo.getStatus())) {
                    row.createCell(ExcelRowEnum.NINTHROW.getValue()).setCellValue(ExportEnum.NORMAL.getValue());
                } else {
                    row.createCell(ExcelRowEnum.NINTHROW.getValue()).setCellValue(ExportEnum.FREEZE.getValue());
                }
            }
            try {
                FileOutputStream fos = new FileOutputStream(EXPORTFILEPATH + ExportEnum.RUSERLIST.getValue() + ExportEnum.XLS.getValue());
                wb.write(fos);
                fos.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
            taskService.updateExportTask(EXPORTFILEPATH + ExportEnum.RUSERLIST.getValue() + ExportEnum.XLS.getValue(), exportTaskparams);
            exportTaskInfo = taskService.getExportTaskInfo(exportTaskparams.getId());
        }
        return exportTaskInfo;
    }

}
