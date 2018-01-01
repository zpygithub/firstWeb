package com.firstWeb.constant;

public enum UserSexEnum {
    /**
     * 未知
     */
    UNKNOWN(0),
    /**
     * 男
     */
    MALE(1),
    /**
     * 女
     */
    FEMALE(2);

    private int value;

    UserSexEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
