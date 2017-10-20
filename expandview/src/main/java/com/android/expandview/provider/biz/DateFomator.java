package com.android.expandview.provider.biz;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.text.SimpleDateFormat;

public class DateFomator {

    public static final Uri URI_RECORD_DAILY = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordDaily");
    public static final Uri URI_RECORD_ALBUM = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordAlbum");
    public static final Uri URI_RECORD_EPISODE = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordEpisode");

    public static String getCurrentDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    public static void deleteAllQiYiRecord(Context context) {
        ContentResolver resolver = context.getContentResolver();
        try {
            resolver.delete(URI_RECORD_ALBUM, null, null);
            resolver.delete(URI_RECORD_DAILY, null, null);
            resolver.delete(URI_RECORD_EPISODE, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPlayTime(long milions) {
        long hour = milions / (60 * 60 * 1000);
        long minute = (milions - hour * 60 * 60 * 1000) / (60 * 1000);
        long second = (milions - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
        StringBuilder time = new StringBuilder();
        time.append(hour == 0 ? "" : hour + "小时")
                .append(minute == 0 ? "" : minute + "分")
                .append(second).append("秒");
        return time.toString();
    }

}
