package com.firstWeb.bean.response;

import com.firstWeb.constant.UserSexEnum;
import com.firstWeb.constant.UserStatusEnum;
import com.firstWeb.constant.ExportEnum;

import java.util.Date;

public class RegisterUserInfo extends PageInfo {
    private long id;
    private String account;
    private String username;
    private String sex;
    private String telephone;
    private String email;
    private String district;
    private String address;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if ((UserSexEnum.MALE.getValue() + "").equals(sex)) {
            this.sex = ExportEnum.MALE.getValue();
        } else if ((UserSexEnum.FEMALE.getValue() + "").equals(sex)) {
            this.sex = ExportEnum.FEMALE.getValue();
        } else {
            this.sex = ExportEnum.UNKNOWN.getValue();
        }
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return "RegisterUserInfo{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", sex=" + sex +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", district=" + district +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
