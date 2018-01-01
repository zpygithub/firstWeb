package com.firstWeb.constant;

public enum UserStatusEnum {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 冻结
     */
    FREEZE(-1);

    private int value;

    UserStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
