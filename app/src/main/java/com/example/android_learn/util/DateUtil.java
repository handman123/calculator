package com.example.android_learn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getCurTime(){
        return SIMPLE_DATE_FORMAT.format(new Date());
    }
}
