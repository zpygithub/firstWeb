package com.firstWeb.service;

import com.firstWeb.bean.model.ResourceMenu;
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

    public List<ResourceMenu> getResourceMenusById(long id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", "0");
        List<ResourceMenu> list = systemMapper.getResourceMenusById(map);

        return createMenuTree(list);
    }

    private List<ResourceMenu> createMenuTree(List<ResourceMenu> list) {
        List<ResourceMenu> menus = new ArrayList<ResourceMenu>();
        if (null != list && list.size() > 0) {
            for (ResourceMenu rm : list) {
                if (rm.getParentId() == 0) {
                    menus.add(rm);
//                    creatMenuTree(rm, list);
                }
            }
        }
        return menus;
    }

}
