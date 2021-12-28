package com.srwing.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:处理日期的工具类
 * Created by small small su
 * Date: 2021/12/28
 * Email: surao@foryou56.com
 */
public class DateUtils {
    /**
     * 计算两个日期间隔天数
     *
     * @param startTime ： 开始时间
     * @param endTime   ： 结束时间
     * @param format：   时间格式
     * @return
     */
    public static String caculateTotalTime(String startTime, String endTime, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date1 = null;
        Date date2 = null;
        Long l = 0L;
        try {
            date1 = formatter.parse(startTime);
            date2 = formatter.parse(endTime);
            l = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l.intValue() + 1 + "";
    }
}
