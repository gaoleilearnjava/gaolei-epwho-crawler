package com.gaolei.crawler.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//已经看完

/**
 * 日期工具类
 */
public class DateUtils {

    public static Date parseDate(String date, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.parse(date);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String formatDate(String date, String pattern, String fmt) {
        String result = "";
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(pattern);
            Date d = format.parse(date);
            format = new SimpleDateFormat(fmt);
            result = format.format(d);
        } catch (Exception ex) {
        }
        return result;
    }

    /**
     * 根据开始月份和结束月份返回时间段内的月份集合
     *
     * @param beginDate 开始月份(格式：yyyy-MM)
     * @param endDate   结束月份(格式：yyyy-MM)
     * @return 月份集合(格式 ： yyyy - MM)
     */
    public static List<String> getMonthBetween(String beginDate, String endDate) {
        List<String> result = new ArrayList<String>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(beginDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(endDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param beginDate 开始日期(格式：yyyy-MM-dd)
     * @param endDate   结束日期(格式：yyyy-MM-dd)
     * @return List 时间集合(格式：yyyy-MM-dd)
     */
    public static List<String> getDateBetween(String beginDate, String endDate) {
        List<String> lDate = new ArrayList<String>();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月日

            lDate.add(beginDate);// 把开始时间加入集合
            Calendar cal = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            cal.setTime(sdf.parse(beginDate));
            boolean bContinue = true;
            while (bContinue) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                cal.add(Calendar.DAY_OF_MONTH, 1);
                // 测试此日期是否在指定日期之后
                if (sdf.parse(endDate).after(cal.getTime())) {
                    lDate.add(sdf.format(cal.getTime()));
                } else {
                    break;
                }
            }
            lDate.add(endDate);// 把结束时间加入集合
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lDate;
    }
}
