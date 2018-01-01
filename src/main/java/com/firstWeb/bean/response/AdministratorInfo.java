package com.firstWeb.bean.response;

import com.firstWeb.constant.UserStatusEnum;
import com.firstWeb.constant.ExportEnum;

import java.util.Date;

public class AdministratorInfo extends PageInfo {
    private long id;
    private String account;
    private String username;
    private String telephone;
    private String email;
    private String status;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if ((UserStatusEnum.NORMAL.getValue() + "").equals(status)) {
            this.status = ExportEnum.NORMAL.getValue();
        } else {
            this.status = ExportEnum.FREEZE.getValue();
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AdministratorInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
