package com.firstWeb.util;

/**
 * Created by zpy on 2017/4/24.
 */
public class ParamValidateUtil {

    /**
     * 数字正则
     */
    private static final String IS_NUMBER = "\\d+";

    /**
     * 账号正则（字母+数字）
     */
    private static final String IS_ACCOUNT = "[0-9a-zA-Z]{6,20}";

    /**
     * 昵称正则（任意字符）
     */
    private static final String IS_NICKNAME = ".{2,8}";

    /**
     * 密码正则（字母+数字+特殊字符）
     */
    private static final String IS_PASSWD = "[0-9a-zA-Z`~!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?]{6,20}";

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(String str) {
        if (null == str || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是数字
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches(IS_NUMBER);
    }

    /**
     * 判断账号
     */
    public static boolean isAccount(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches(IS_ACCOUNT);
    }

    /**
     * 判断昵称
     */
    public static boolean isNickname(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches(IS_NICKNAME);
    }

    /**
     * 判断密码
     */
    public static boolean isPasswd(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches(IS_PASSWD);
    }
}
