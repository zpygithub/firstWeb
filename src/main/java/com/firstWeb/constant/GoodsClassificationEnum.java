package com.firstWeb.constant;

public enum GoodsClassificationEnum {
    /**
     * 衣服
     */
    clothes(0),
    /**
     * 生活用品
     */
    dailyNecessities(1);

    private int value;

    GoodsClassificationEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
