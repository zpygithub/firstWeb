package com.firstWeb.service;

import com.firstWeb.bean.model.MainMenu;
import com.firstWeb.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zpy on 2017/2/13.
 */
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
            for (MainMenu rm : list) {
                if (rm.getParentId() == 0) {
                    menus.add(rm);
//                    creatMenuTree(rm, list);
                }
            }
        }
        return menus;
    }

}
