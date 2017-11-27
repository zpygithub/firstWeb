package com.firstWeb.service;

import com.firstWeb.bean.model.Administrator;
import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableTransactionManagement
public class SystemService {

    @Autowired
    private SystemMapper systemMapper;

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

    public List<Administrator> getAdminListOnCondition(AdministratorParam params) {
        List<Administrator> list = systemMapper.getAdminListOnCondition(params);
        return list;
    }

    public String modifyAdminInfo(AdministratorParam param) {
        systemMapper.modifyAdminInfo(param);
        return ResultCode.SUCCESS;
    }

    public Administrator getAdministratorById(long id) {
        Administrator administrator = systemMapper.getAdministratorById(id);
        return administrator;
    }

    public Boolean checkOwnNickname(String nickname, String id) {
        String oldNickname = systemMapper.checkOwnNickname(Long.valueOf(id));
        if (nickname.equals(oldNickname)) {
            return true;
        }
        return false;
    }
}
