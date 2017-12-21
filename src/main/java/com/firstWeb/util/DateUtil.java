package com.firstWeb.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getUTCDate() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return new Date(cal.getTimeInMillis());
    }

    public static Date getLocalTimeFromUTC(Date utcTime)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(utcTime);
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, +(zoneOffset + dstOffset));
        return cal.getTime();
    }
}
