package com.firstWeb.common;

import com.firstWeb.bean.response.PageInfo;

import java.util.List;

/**
 * Created by zpy on 2017/4/20.
 */
public class CollectionResult<T> {

    private List<T> list;
    private PageInfo pageInfo;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
