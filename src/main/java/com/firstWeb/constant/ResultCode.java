package com.firstWeb.constant;

public interface ResultCode {
    /**
     * 成功
     */
    String SUCCESS = "00000";
    /**
     * 失败
     */
    String FAIL = "00001";
    /**
     * 参数异常
     */
    String PARAMEXCEPTION = "00002";
    /**
     * 参数不能为空
     */
    String PARAMCANNOTBENULL = "00003";
    /**
     * 注册密码不相同
     */
    String PASSWDCANNOTBRDIFFRENT = "00004";
    /**
     * 账号已存在
     */
    String ACCOUNTISEXISTENCE = "00005";
    /**
     * 用户名已存在
     */
    String USERNAMEISEXISTENCE = "00006";
    /**
     * 账号格式错误
     */
    String ACCOUNTFORMATEXCEPTION = "00007";
    /**
     * 用户名格式错误
     */
    String USERNAMEFORMATEXCEPTION = "00008";
    /**
     * 密码格式错误
     */
    String PASSWDFORMATEXCEPTION = "00009";
    /**
     * 账号不存在
     */
    String ACCOUNTISNOTEXISTENCE = "00010";
    /**
     * 账号已被锁
     */
    String ACCOUNTISLOCKED = "00011";
    /**
     * 导出任务正在进行
     */
    String EXPORTTASKISRUNNING = "00012";
}
