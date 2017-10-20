package com.android.expandview.provider.biz;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.android.expandview.bean.VideoRecordEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 10/18/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 10/18/2017 do fisrt create. </li>
 */

public class PremierHistory {

    public static final Uri RECORD_PREMIER = Uri.parse("content://com.hiveview.provider.historyProvider/tb_history");

    public static PremierHistory get() {
        return SingleHolder.HOLDER;
    }

    public List<VideoRecordEntity> getHistory(Context context, List<String> ids) {
        List<VideoRecordEntity> premierHistory = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(RECORD_PREMIER, null, null, null, null);
        if (null != cursor) {
            while (cursor.moveToNext()) {
                if (ids.contains(cursor.getString(cursor.getColumnIndex("albumId")))) {
                    continue;
                }
                ids.add(cursor.getString(cursor.getColumnIndex("albumId")));
                VideoRecordEntity entity = new VideoRecordEntity();
                entity.setSource(3);
                entity.setAlbumId(cursor.getString(cursor.getColumnIndex("albumId")));
                entity.setMovieName(cursor.getString(cursor.getColumnIndex("movieName")));
                entity.setPicUrl(cursor.getString(cursor.getColumnIndex("picUrl")));
                // 显示类型，1：电影，2：电视剧
                entity.setVideoset_type(cursor.getInt(cursor.getColumnIndex("showType")));
                //
                entity.setFormatTime(cursor.getLong(cursor.getColumnIndex("recordTime")));
                // 被添加到历史记录的时间
                entity.setRecordTime(cursor.getString(cursor.getColumnIndex("playDate")));
                try {
                    // 上次播放的时间
                    entity.setCurrentEpisode(cursor.getString(cursor.getColumnIndex("lastPlayTime")));
                } catch (Exception | Error e) {
                    e.printStackTrace();
                }
                premierHistory.add(entity);
            }
            cursor.close();
        }
        return premierHistory;
    }

    private static final class SingleHolder {
        private static final PremierHistory HOLDER = new PremierHistory();
    }
}
