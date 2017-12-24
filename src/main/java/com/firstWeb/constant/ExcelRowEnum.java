package com.firstWeb.constant;

public enum ExcelRowEnum {
    FIRSTROW(0),

    SECONDROW(1),

    THIRDROW(2),

    FOURTHROW(3),

    FIFTHROW(4),

    SIXTHROW(5),

    SeventhROW(6),

    EighthROW(7),

    NinthROW(8),

    tenthrow(9);

    private int value;

    ExcelRowEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
