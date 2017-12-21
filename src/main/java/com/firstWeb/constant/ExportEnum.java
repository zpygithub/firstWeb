package com.firstWeb.constant;

public enum ExportEnum {
    adminList("管理员列表"),

    id("序号"),

    account("账号"),

    nickname("昵称"),

    email("邮箱"),

    telephone("电话"),

    createTime("创建时间"),

    status("状态"),

    normal("正常"),

    freeze("冻结");

    private String value;

    ExportEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
