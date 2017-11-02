package com.firstWeb.exception;

/**
 * Created by zpy on 2017/4/24.
 */
public class CommonException extends Exception {

    private String errorCode;
    private String errorMessage;

    public CommonException() {
    }

    public CommonException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
