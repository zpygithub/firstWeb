package com.firstWeb.mapper;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.response.AdministratorInfo;

import java.util.List;

public interface SystemMapper {

    List<MainMenu> getMainMenus();

    List<AdministratorInfo> getAdminListOnCondition(AdministratorParam params);

    int getAdminListSize(AdministratorParam params);

    void modifyAdminInfo(AdministratorParam param);

    void changeAdminStatus(AdministratorParam param);

    AdministratorInfo getAdministratorById(long id);

    String checkOwnUsername(long id);

    List<AdministratorInfo> exportAdminList(AdministratorParam params);
}
