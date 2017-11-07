package com.firstWeb.bean.model;

import java.util.Date;
import java.util.List;

public class MainMenu {
    private long id;
    private String menuName;
    private long parentId;
    private int status;
    private Date createTime;
    private String uri;
    private List<MainMenu> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public List<MainMenu> getChildren() {
        return children;
    }

    public void setChildren(List<MainMenu> children) {
        this.children = children;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
