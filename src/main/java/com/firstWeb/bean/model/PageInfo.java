package com.firstWeb.bean.model;

/**
 * Created by zpy on 2017/4/20.
 */
public class PageInfo {

    private int totalRecords;
    private int from;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
