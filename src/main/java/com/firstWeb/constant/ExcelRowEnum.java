package com.firstWeb.constant;

public enum ExcelRowEnum {
    FIRSTROW(0),

    SECONDROW(1),

    THIRDROW(2),

    FOURTHROW(3),

    FIFTHROW(4),

    SIXTHROW(5),

    SEVENTHROW(6),

    EIGHTHROW(7),

    NINTHROW(8),

    TENTHROW(9);

    private int value;

    ExcelRowEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
