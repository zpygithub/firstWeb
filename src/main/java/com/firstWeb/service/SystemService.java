package com.firstWeb.service;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.bean.param.AdministratorParam;
import com.firstWeb.bean.response.AdministratorInfo;
import com.firstWeb.bean.response.PageInfo;
import com.firstWeb.common.CollectionResult;
import com.firstWeb.constant.ResultCode;
import com.firstWeb.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

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
}
