package com.firstWeb.util;

import com.firstWeb.constant.ResultCode;
import com.firstWeb.exception.CommonException;

/**
 * Created by zpy on 2017/4/24.
 */
public class BasicDateValidateUtil {
    /**
     * 是否为空
     */
    public static void validateIsEmpty(String str) throws CommonException {
        if (ParamValidateUtil.isEmpty(str)) {
            throw new CommonException(ResultCode.PARAMCANNOTBENULL, "the param can not be null");
        }
    }

    /**
     * 是否数字
     */
    public static void validateIsNumber(String str) throws CommonException {
        if (!ParamValidateUtil.isNumber(str)) {
            throw new CommonException(ResultCode.PARAMEXCEPTION, "the param can not be null");
        }
    }

    /**
     * 账号是否合法
     */
    public static void validateIsAccount(String str) throws CommonException {
        if (!ParamValidateUtil.isAccount(str)) {
            throw new CommonException(ResultCode.ACCOUNTFORMATEXCEPTION, "the account format exist error");
        }
    }

    /**
     * 用户名是否合法
     */
    public static void validateIsUsername(String str) throws CommonException {
        if (!ParamValidateUtil.isUsername(str)) {
            throw new CommonException(ResultCode.USERNAMEFORMATEXCEPTION, "the username format exist error");
        }
    }

    /**
     * 密码是否合法
     */
    public static void validateIsPasswd(String str) throws CommonException {
        if (!ParamValidateUtil.isPasswd(str)) {
            throw new CommonException(ResultCode.PASSWDFORMATEXCEPTION, "the password format exist error");
        }
    }
}
