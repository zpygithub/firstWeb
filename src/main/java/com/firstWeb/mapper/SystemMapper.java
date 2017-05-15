package com.firstWeb.mapper;

import com.firstWeb.bean.model.ResourceMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by zpy on 2017/2/13.
 */
public interface SystemMapper {

    List<ResourceMenu> getResourceMenusById(Map map);

}
