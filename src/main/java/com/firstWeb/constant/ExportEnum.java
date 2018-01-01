package com.firstWeb.constant;

public enum ExportEnum {
    EXPORT("导出"),

    ADMINLIST("管理员列表"),

    ID("序号"),

    ACCOUNT("账号"),

    USERNAME("用户名"),

    EMAIL("邮箱"),

    TELEPHONE("电话"),

    CREATETIME("创建时间"),

    STATUS("状态"),

    NORMAL("正常"),

    FREEZE("冻结"),

    UNKNOWN("未知"),

    MALE("男"),

    FEMALE("女");

    private String value;

    ExportEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
