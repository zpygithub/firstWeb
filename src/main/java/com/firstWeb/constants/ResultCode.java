package com.firstWeb.constants;

/**
 * Created by zpy on 2017/4/22.
 */
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
     * 昵称已存在
     */
    String NICKNAMEISEXISTENCE = "00006";
}
