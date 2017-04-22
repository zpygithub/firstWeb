package com.firstWeb.common;

/**
 * Created by zpy on 2017/4/20.
 */
public class ResultEntity<T> {

    private String code;
    private T value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
