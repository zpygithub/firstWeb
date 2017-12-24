package com.firstWeb.constant;

public enum ExportStatusEnum {
    /**
     * 失败
     */
    FAIL(-1),
    /**
     * 正在导出
     */
    EXPORTISRUNNING(0),
    /**
     * 成功
     */
    SUCCESS(1),
    /**
     * 导出为空
     */
    EXPORTNODATA(2);

    private int value;

    ExportStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
