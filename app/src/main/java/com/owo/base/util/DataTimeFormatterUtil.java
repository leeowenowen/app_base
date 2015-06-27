package com.owo.base.util;

/**
 * Created by wangli on 15-6-5.
 */
public class DataTimeFormatterUtil {
    public static String formatTime(long duration) { //in seconds
        if(duration < 0)
        {
            return "刚刚";
        }
        String ret = "";
        int days = (int) duration / (60 * 60 * 24);
        if (days > 0) {
            ret += ("" + days + "天");
        }
        duration = duration - (days * (60 * 60 * 24));
        int hours = (int) duration / (60 * 60);
        if (hours > 0) {
            ret += ("" + hours + "小时");
        }
        duration = duration - (hours * (60 * 60));
        int mins = (int) duration / (60);
        if (mins > 0) {
            ret += ("" + mins + "分钟");
        }
        duration = duration - (duration * 60);
        int seconds = (int) duration;
        if (seconds > 0) {
            ret += ("" + seconds + "秒");
        }
        if (ret.length() > 0) {
            ret += "前";
        }
        return ret;
    }

}
