package com.firstWeb.util;

import java.util.Date;

public class DateUtil {
    public static Date getUTCDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return new Date(cal.getTimeInMillis());
    }
}
