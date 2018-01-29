package com.firstWeb.constant;

public enum ExportEnum {
    EXPORT("导出"),

    XLS(".xls"),

    COLONREG(":"),

    HYPHENREG("-"),

    BLANKREG(" "),

    EXPORTADMINLIST("export_adminList_"),

    ADMINLIST("AdminList"),

    ADMINISTRATORLIST("管理员列表"),

    EXPORTREGISTERUSERLIST("export_registerUserList_"),

    RUSERLIST("RegisterUserList"),

    REGISTERUSERLIST("注册用户列表"),

    ID("序号"),

    ACCOUNT("账号"),

    USERNAME("用户名"),

    SEX("性别"),

    EMAIL("邮箱"),

    TELEPHONE("电话"),

    DISTRICT("地区"),

    ADDRESS("地址"),

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
