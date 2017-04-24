package com.firstWeb.utils;

/**
 * Created by zpy on 2017/4/24.
 */
public class ParamValidateUtil {

    /**
     * 数字正则
     */
    private static final String IS_NUMBER = "\\d+";

    /**
     * 判断是否为空
     */
    public static boolean isEmpty(String str) {
        return str.matches(IS_NUMBER);
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
}
