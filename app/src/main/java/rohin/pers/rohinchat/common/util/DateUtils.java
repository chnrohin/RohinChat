/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rohin.pers.rohinchat.common.util;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class DateUtils {

    private static Date mDate;

    private static DateFormat mDateFormat;

    /**
     * 时间戳格式
     * 0, default   :   yyyy年MM月dd日 HH:mm;
     * 1, long      :   yyyy年MM月dd日
     * 2, short     :   HH:mm
     */
    public enum DatePattern {

        DEF("yyyy年MM月dd日 HH:mm", 0x01),

        LONG("yyyy年MM月dd日", 0x02),

        SHORT("MM月dd日", 0x03),

        TIME("HH:mm", 0x04),

        WEEK("EEEE", 0x05);

        final int key;

        final String value;

        DatePattern(String value, int key) {
            this.value = value;
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 指[定|派]日期区间
     * HOUR - 当天 - HH:mm
     * DAY  - 昨天
     * WEEK - 当周 - 周[日 ~ 六]
     * YEAR - 本年 - MM月dd日
     */
    public enum DateRange {

        DEFAULT, CURR_YEAR, CURR_WEEK, YESTERDAY
    }

    private String pattern;

    private static String timestamp;

    private static final String[] WEEK_DAYS = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    private static final String DEF_PATTERN = "yyyy年MM月dd日";

    private static final String DEF_TIME_STAMP = "1970年01月01日";

    private static final Long YEAR_MILLIS = 365L * 86400000L;

    private static final long WEEK_MILLIS = 604800000;

    private static final long DAY_MILLIS = 86400000;

    private static final long HOUR_MILLIS = 3600000;

    private static final long MINUTES_MILLIS = 60000;

    /**
     * 获取当前[天|周|年]从零点开始 Millis
     */
    private static long getZeroOfRange(@NonNull DateRange range) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        long zeroOfRange = calendar.getTimeInMillis();
        switch (range) {
            case YESTERDAY:
                zeroOfRange = zeroOfRange - DAY_MILLIS;
                break;
            case CURR_WEEK:
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                zeroOfRange = calendar.getTimeInMillis();
                break;
            case CURR_YEAR:
                calendar.set(Calendar.DAY_OF_YEAR, 0);
                zeroOfRange = calendar.getTimeInMillis();
                break;
            default:
                break;
        }
        return zeroOfRange;
    }

    /**
     * 星期
     */
    private static String getWeekOfDate(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (index < 0) index = 0;
        return WEEK_DAYS[index];
    }

    /**
     * 转换为指定格式的时间戳
     *
     * 已知Bug:零点到一点显示昨天。
     *
     * @param time 要转换的具体时间
     */
    public static String getTimestamp(long time) {
        // January 1, 1970 08:00:00.000 GMT (Gregorian)
        long now = System.currentTimeMillis();
        if (time > now && time < 0) {
            LogUtils.e("IllegalArgument : " + time);
            return DEF_TIME_STAMP;
        }
        String pattern;
        mDate = new Date(time);

        if (time > getZeroOfRange(DateRange.DEFAULT)) { // 当日
            pattern = DatePattern.TIME.getValue();
        } else if (time > getZeroOfRange(DateRange.YESTERDAY)) { // 昨天

            return timestamp = "昨天";
        } else if (time > getZeroOfRange(DateRange.CURR_WEEK)) { // 本周

            return timestamp = getWeekOfDate(mDate);
        } else if (time > getZeroOfRange(DateRange.CURR_YEAR)) { // 本年度
            pattern = DatePattern.SHORT.getValue();
        } else {                    // 默认显示DatePattern.LONG.getValue()
            pattern = DEF_PATTERN;
        }
        mDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        timestamp = mDateFormat.format(mDate);
        return timestamp;
    }

    /**
     * 将给定字符串以指定格式
     */
    public static long formatDate(@NonNull String date, @NonNull String pattern) {
        if ("".equals(pattern)) pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        Date d = simpleDateFormat.parse(date, pos);
        return d.getTime();
    }

    /**
     * 随机返回一个指定开始->结束时间内日期
     */
    public static long randomDate(long begin, long end) {
        long random = begin + (long) (Math.random() * (end - begin));
        if (random == begin || random == end) {
            return randomDate(begin, end);
        }
        return random;
    }

}
