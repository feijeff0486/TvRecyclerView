/**
 * @Title QIYIUtils.java
 * @Package com.hiveview.cloudscreen.video.utils
 * @author haozening
 * @date 2014年11月25日 上午11:27:01
 * @Description
 * @version V1.0
 */
package com.android.expandview.provider.biz;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.android.expandview.bean.VideoRecordEntity;
import com.gogh.okrxretrofit.util.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author haozening
 * @ClassName QIYIUtils
 * @Description
 * @date 2014年11月25日 上午11:27:01
 */
public class IQiYiHistory {

    public static final Uri RECORD_QIYI = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordDaily");
    public static final Uri RECORD_CONTROLLER = Uri.parse("content://HiveViewCloudPlayerAuthorities/RecordController");
    private static final String TAG = "IQiYiHistory";
    public static List<String> ids = Collections.synchronizedList(new ArrayList<String>());

    public static List<VideoRecordEntity> getHistoryList(Context context, String startTime, String endTime) {

        List<VideoRecordEntity> qiyiList = new ArrayList<>();
        if (null == context) {
            return qiyiList;
        }
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(RECORD_QIYI, null, "recordTime between ? and ?", new String[]{startTime, endTime}, "recordTime");
        if (null != cursor) {
            while (cursor.moveToNext()) {
                if (ids.contains(cursor.getString(cursor.getColumnIndex("albumId")))) {
                    continue;
                }
                ids.add(cursor.getString(cursor.getColumnIndex("albumId")));
                VideoRecordEntity entity = new VideoRecordEntity();
                entity.setSource(0);
                entity.setMovieName(cursor.getString(cursor.getColumnIndex("movieName")));
                entity.setPicUrl(cursor.getString(cursor.getColumnIndex("picUrl")));
                entity.setRecordTime(cursor.getLong(cursor.getColumnIndex("recordTime")));
                entity.setVideoset_type(cursor.getInt(cursor.getColumnIndex("videoset_type")));
                entity.setAlbumId(cursor.getString(cursor.getColumnIndex("albumId")));
                entity.setVrsAlbumId(cursor.getString(cursor.getColumnIndex("vrsAlbumId")));
                entity.setFormatTime(cursor.getLong(cursor.getColumnIndex("recordTime")));
                entity.setVideoset_id(cursor.getInt(cursor.getColumnIndex("videoset_id")));
                try {
                    entity.setCurrentEpisode(cursor.getString(cursor.getColumnIndex("currentEpisode")));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
                qiyiList.add(entity);
            }
            cursor.close();
        }
        List<VideoRecordEntity> premierHistory = PremierHistory.get().getHistory(context, ids);
        Logger.d(TAG, "premierHistory " + premierHistory.toString());
        if (premierHistory != null && premierHistory.size() > 0) {
            qiyiList.addAll(premierHistory);
        }
        ids.clear();
        return qiyiList;
    }

    public static boolean delete(Context context, VideoRecordEntity entity) {
        Bundle extras = new Bundle();
        extras.putInt("programsetId", entity.getVideoset_id());
        extras.putInt("videoId", entity.getVideoset_id());
        // TODO 删除数据库内指定的历史记录
        ContentResolver resolver = context.getContentResolver();
        resolver.call(RECORD_QIYI, "deleteRelations", null, extras);
        return true;
    }

}
