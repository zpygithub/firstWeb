package com.firstWeb.bean.response;

public class PageInfo {
    private int currentPage;
    private int perPageRecords;
    private int totalRecords;
    private int from;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPerPageRecords() {
        return perPageRecords;
    }

    public void setPerPageRecords(int perPageRecords) {
        this.perPageRecords = perPageRecords;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int currentPage, int perPageRecord) {
        this.from = perPageRecord * (currentPage - 1);
    }
}
