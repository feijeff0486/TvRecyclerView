package com.gogh.rxretrofit;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 8/15/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 8/15/2017 do fisrt create. </li>
 */

public class LauncherApplication extends Application {

    private static final String TAG = "LauncherApplication";

    private String getAppVersion() {
        try {
            PackageInfo packInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (packInfo != null) {
                return packInfo.versionName;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return "0";
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /* 加载新的补丁包*/
//        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void initSophix() {
        // initialize最好放在attachBaseContext最前面
        String appVersion = getAppVersion();
        Log.d(TAG, "appVersion : " + appVersion);
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        Log.d(TAG, "mode : " + mode);
                        Log.d(TAG, "code : " + code);
                        Log.d(TAG, "info : " + info);
                        Log.d(TAG, "handlePatchVersion : " + handlePatchVersion);
                    /* 补丁加载回调通知*/
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        /*表明补丁加载成功*/
                            Log.d(TAG, "CODE_LOAD_SUCCESS");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            Log.d(TAG, "CODE_LOAD_RELAUNCH");
                        /* 表明新补丁生效需要重启. 可提示用户或者强制重启;
                        建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁*/
                            SophixManager.getInstance().killProcessSafely();
                        } else {
                            Log.d(TAG, "else");
                         /*其它错误信息, 查看PatchStatus类说明*/

                        }
                    }
                }).initialize();
    }

}
