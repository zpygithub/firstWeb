package com.firstWeb.mapper;

import com.firstWeb.bean.model.Administrator;
import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;

import java.util.List;
import java.util.Map;

public interface SystemMapper {

    List<MainMenu> getMainMenus();

    String modifyAdminInfo(AdministratorParam param);

    Administrator getAdministratorById(long id);

}
