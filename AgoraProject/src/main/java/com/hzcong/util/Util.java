package com.hzcong.util;

import java.util.Calendar;
import java.util.UUID;

public class Util {

    public static String createRandom32Chars(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createTimeChars(){
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.YEAR)) + (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH)
                + now.get(Calendar.HOUR_OF_DAY) + now.get(Calendar.MINUTE) + now.get(Calendar.SECOND) +
                now.getTimeInMillis();
    }
}
