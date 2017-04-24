package com.firstWeb.utils;

import com.firstWeb.constants.ResultCode;
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
}
