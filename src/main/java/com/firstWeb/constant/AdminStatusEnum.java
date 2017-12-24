package com.firstWeb.constant;

public enum AdminStatusEnum {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 冻结
     */
    FREEZE(-1);

    private int value;

    AdminStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
