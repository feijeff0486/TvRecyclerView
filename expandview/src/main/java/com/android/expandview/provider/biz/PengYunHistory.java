package com.android.expandview.provider.biz;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.expandview.bean.CollectEntity;
import com.android.expandview.bean.VideoRecordEntity;
import com.android.expandview.common.CollectionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2017/6/26
 * @Description
 */

public class PengYunHistory {

    private static final String TAG = PengYunHistory.class.getSimpleName();

    private static final Uri RECORD_PY = Uri.parse("content://com.hiveview.cloudscreen.pyplayer/RecordDaily");

    private static final Uri COLLECT_PY = Uri.parse("content://com.hiveview.cloudscreen.py/album_collection");

    private static List<String> IDS = new ArrayList<>();

    public static List<VideoRecordEntity> getHistoryList(Context context, String startTime, String endTime) {
        try {
            List<VideoRecordEntity> pyList = new ArrayList<>();
            if (null == context) {
                return pyList;
            }
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(RECORD_PY, null, "recordTime between ? and ?", new String[]{startTime, endTime}, "recordTime");
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    if (IDS.contains(cursor.getString(cursor.getColumnIndex("albumId")))) {
                        continue;
                    }
                    IDS.add(cursor.getString(cursor.getColumnIndex("albumId")));
                    VideoRecordEntity entity = new VideoRecordEntity();
                    entity.setSource(2);
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
                    pyList.add(entity);
                }
                IDS.clear();
                cursor.close();
            }
            Log.d(TAG, "pyList=" + pyList.toString());
            return pyList;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getHistoryList failed e=" + e.toString());
            return new ArrayList<>();
        }
    }

    public static boolean delete(Context context, VideoRecordEntity entity) {
        try {
            Bundle extras = new Bundle();
            extras.putInt("programsetId", entity.getVideoset_id());
            extras.putInt("videoId", entity.getVideoset_id());
            // TODO 删除数据库内指定的历史记录
            ContentResolver resolver = context.getContentResolver();
            resolver.call(RECORD_PY, "deleteRelations", null, extras);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "delete failed e=" + e.toString());
            return false;
        }
    }

    public static boolean realDelete(Context context, CollectEntity entity) {
        ContentResolver resolver = context.getContentResolver();
        String where = CollectionDao.COLUMN_ID + " = ? AND " + CollectionDao.COLUMN_USER_ID + " = ? ";
        String[] selectionArgs = new String[]{entity.getCollectId() + "", UserState.getInstance().getUserInfo().userId};
        int columnResult = resolver.delete(COLLECT_PY, where, selectionArgs);
        Log.d(TAG, "columnResult re=" + columnResult);
        if (columnResult == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static List<CollectEntity> getCollectList(Context context, String where, String[] selectionArgs) {
        try {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(COLLECT_PY, null, where, selectionArgs, CollectionDao.COLUMN_COLLECT_TIME + " desc");
            List<CollectEntity> list = new ArrayList<CollectEntity>();
            while (cursor.moveToNext()) {
                CollectEntity entity = new CollectEntity();
                entity.setSource(2);
                entity.setCid(cursor.getInt(cursor.getColumnIndex(CollectionDao.COLUMN_CID)));
                entity.setName(cursor.getString(cursor.getColumnIndex(CollectionDao.COLUMN_NAME)));
                entity.setEpisodeUpdate(cursor.getInt(cursor.getColumnIndex(CollectionDao.COLUMN_EPISODE_UPDATED)));
                entity.setCpId(cursor.getInt(cursor.getColumnIndex(CollectionDao.COLUMN_CP_ID)));
                entity.setCollectTime(cursor.getLong(cursor.getColumnIndex(CollectionDao.COLUMN_COLLECT_TIME)));
                entity.setCollectId(cursor.getInt(cursor.getColumnIndex(CollectionDao.COLUMN_ID)));
                entity.setPicUrl(cursor.getString(cursor.getColumnIndex(CollectionDao.COLUMN_PIC_URL)));
                entity.setAlbumId(cursor.getInt(cursor.getColumnIndex(CollectionDao.COLUMN_ID)) + "");
                entity.setEpisodeTotal(cursor.getInt(cursor.getColumnIndex(CollectionDao.COLUMN_EPISODE_TOTAL)));
                if (TextUtils.isEmpty(entity.getPicUrl()) || "null".equals(entity.getPicUrl())) {
                    entity.setBlueRayImg(cursor.getString(cursor.getColumnIndex(CollectionDao.COLUMN_BLUE_RAY_IMG)));
                }
                list.add(entity);
            }
            cursor.close();
            Log.d(TAG, "where=" + where);
            Log.d(TAG, "selectionArgs=" + selectionArgs[0]);
            Log.d(TAG, "getCollectList list=" + list.toString());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getCollectList failed e=" + e.toString());
            return new ArrayList<>();
        }
    }

    public static void updateSynFromId(Context context, Integer collectId, Integer synState, String userId, boolean isChangeCollectTime) {
        try {
            ContentResolver resolver = context.getContentResolver();
            ContentValues values = new ContentValues();
            if (isChangeCollectTime) {
                values.put(CollectionDao.COLUMN_COLLECT_TIME, System.currentTimeMillis() + "");
            }
            values.put(CollectionDao.COLUMN_SYN_STATE, synState);
            String where = "";
            String[] selectionArgs;
            if (null != collectId) {
                where = CollectionDao.COLUMN_ID + " = ? AND " + CollectionDao.COLUMN_USER_ID + " = ? ";
                selectionArgs = new String[]{collectId + "", userId};
            } else {
                where = CollectionDao.COLUMN_USER_ID + " = ? ";
                selectionArgs = new String[]{userId};
            }
            int re = resolver.update(COLLECT_PY, values, where, selectionArgs);
            Log.d(TAG, "synState=" + synState);
            Log.d(TAG, "updateSynFromId re=" + re);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "updateSynFromId failed e=" + e.toString());
        }
    }

    public static boolean deleteAllCollect(Context context) {
        try {
            ContentResolver resolver = context.getContentResolver();
            String where = CollectionDao.COLUMN_USER_ID + " = ? ";
            String[] selectionArgs = new String[]{UserState.getInstance().getUserInfo().userId};
            int columnResult = resolver.delete(COLLECT_PY, where, selectionArgs);
            if (columnResult == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "deleteAllCollect failed e=" + e.toString());
            return false;
        }
    }

    public static void insertCollectEntity(Context context, CollectEntity entity) {
        try {
            ContentResolver resolver = context.getContentResolver();
            if (null != entity) {
                ContentValues values = new ContentValues();
                values.put(CollectionDao.COLUMN_ID, entity.getCollectId());
                values.put(CollectionDao.COLUMN_COLLECT_TIME, entity.getCollectTime());
                values.put(CollectionDao.COLUMN_BLUE_RAY_IMG, entity.getBlueRayImg());
                values.put(CollectionDao.COLUMN_CID, entity.getCid());
                values.put(CollectionDao.COLUMN_CP_ID, entity.getCpId());
                values.put(CollectionDao.COLUMN_EPISODE_TOTAL, entity.getEpisodeTotal());
                values.put(CollectionDao.COLUMN_EPISODE_UPDATED, entity.getEpisodeUpdate());
                values.put(CollectionDao.COLUMN_NAME, entity.getName());
                values.put(CollectionDao.COLUMN_PIC_URL, entity.getPicUrl());
                values.put(CollectionDao.COLUMN_SYN_STATE, 0);
                values.put(CollectionDao.COLUMN_USER_ID, UserState.getInstance().getUserInfo().userId);
                resolver.insert(COLLECT_PY, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "insertCollectEntity failed e=" + e.toString());
        }
    }
}
