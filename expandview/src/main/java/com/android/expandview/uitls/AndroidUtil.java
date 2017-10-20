package com.android.expandview.uitls;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.expandview.bean.AppInfoEntity;
import com.gogh.okrxretrofit.util.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: 应用属性相关</p>
 * <p> Created by <b>高晓峰</b> on 7/19/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 7/19/2017 do fisrt create. </li>
 */

public class AndroidUtil {

    private static final String TAG = "AndroidUtil";

    /**
     * 应用市场的uri provider
     */
    private static final String INSTALL_APK_INFO_URI = "content://HiveViewCloudAppStoreAuthorities";

    /**
     * 从appstore 获取安装应用包名
     */
    private static final String GET_APPINSTALL_INFO = "GET_APPINSTALL_INFO";

    /**
     * 根据包名获取应用的相关信息
     *
     * @param packagenName
     * @param context      ChangeLog:
     *                     <li> 高晓峰 on 8/16/2017 </li>
     * @author 高晓峰
     * @date 8/16/2017
     */
    public static AppInfoEntity getAppInfoByPackageName(String packagenName, Context context) {
        if (TextUtils.isEmpty(packagenName)) {
            return null;
        }
        Map<String, String> localApps = null;

        try {
            ContentResolver contentResolver = context.getContentResolver();
            Bundle bundle = contentResolver.call(Uri.parse(INSTALL_APK_INFO_URI), GET_APPINSTALL_INFO, null, null);
            localApps = (HashMap<String, String>) bundle.getSerializable(GET_APPINSTALL_INFO);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PackageInfo pacakgeInfo = null;
        PackageManager packageManager = context.getPackageManager();

        try {
            pacakgeInfo = packageManager.getPackageInfo(packagenName, 0);
            if (pacakgeInfo == null) {
                return null;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String packageName = pacakgeInfo.applicationInfo.packageName;

        if (!hasLaunchIntentForPackage(context, packageName)) {
            Logger.d(TAG, "getAppInfoByPackageName not include launcher category.");
            return null;
        }

        if ((pacakgeInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
            Logger.d(TAG, "getInstallApps is system app.");
            return null;
        }

        AppInfoEntity entity = new AppInfoEntity();
        entity.setAppName(packageManager.getApplicationLabel(pacakgeInfo.applicationInfo).toString());// 应用名称
        entity.setPackageName(packageName);
        entity.setIconDrawable(pacakgeInfo.applicationInfo.loadIcon(packageManager));
        entity.setVersionName(pacakgeInfo.versionName);
        entity.setVersionCode(pacakgeInfo.versionCode);
        entity.setStartOrder(packageName);
        entity.setSystemApp(false);
        if (null != localApps && null != localApps.get(packageName)) {
            Logger.d(TAG, "getInstallApps include appStore : " + pacakgeInfo.applicationInfo.packageName);
            entity.setOnlineIcon(localApps.get(packageName));
        }

        return entity;
    }

    /**
     * 判断应用是否带有Launcher的属性（即有前台页面）
     *
     * @param
     * @ChangeLogger: <li> 高晓峰 on 8/1/2017 </li>
     * @author 高晓峰
     * @date 8/1/2017
     */
    public static boolean hasLaunchIntentForPackage(Context context, String packageName) {
        Logger.d(TAG, "hasLaunchIntentForPackage. ");
        if (TextUtils.isEmpty(packageName)) {
            Logger.d(TAG, "hasLaunchIntentForPackage package name is null. ");
            return false;
        }

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName.trim());
        if (intent == null) {
            Logger.d(TAG, "hasLaunchIntentForPackage get intent for package: " + packageName + " is null.");
            return false;
        }

        return true;
    }

    /**
     * 是否安装了应用
     *
     * @param context
     * @param packageName
     * @ChangeLog: <li> 高晓峰 on 8/14/2017 </li>
     * @author 高晓峰
     * @date 8/14/2017
     */
    public static boolean isInstall(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName) || context == null) {
            return false;
        }

        PackageInfo packageInfo;
        try {
            Logger.d(TAG, "isInstall pkg : " + packageName);
            packageInfo = context.getPackageManager().getPackageInfo(packageName.trim()
                    , PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.d(TAG, "isInstall NameNotFoundException : ");
            //			packageInfo = null;
            e.printStackTrace();
            return false;
        }
        if (packageInfo == null) {
            Logger.d(TAG, "isInstall failed : ");
            return false;
        } else {
            Logger.d(TAG, "isInstall succesfully : ");
            return true;
        }
    }

    /**
     * 根据包名获取版本号
     *
     * @author 高晓峰
     * @date 2016/5/25
     * ChangeLog:
     * <li> 高晓峰 on 2016/5/25 </li>
     */
    public static int getVersionByPkg(Context context, String packageName) {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packInfo == null) {
                return 0;
            }
            return packInfo.versionCode;
        } catch (Throwable t) {
            // 不抛异常，返回空值
            return 0;
        }
    }

    /**
     * 启动应用市场下载安装应用，默认开启提示
     *
     * @author 高晓峰
     * @date 2016/6/10
     * ChangeLog:
     * <li> 高晓峰 on 2016/6/10 </li>
     */
   /* public static void launchAppStore(Context context, String appUrl) {
        if (!TextUtils.isEmpty(appUrl)) {
            Logger.e(TAG, " launch app store download apk.url = " + appUrl);
            Intent intent = new Intent();
            intent.setAction(Actions.ACTION_DOWNLOAD_APP);
            intent.putExtra(Params.Download.APP_URL, appUrl);
            intent.putExtra(Params.Download.SHOW_TOAST, false);
            context.sendBroadcast(intent);
        } else {
            Logger.e(TAG, " launch app store failed, it's an empty url. ");
        }
    }*/

    public static void startAppByPackage(Context context, String packageName) {
        Logger.d(TAG, " startAppByPackage  name =" + packageName);
        if (!TextUtils.isEmpty(packageName) && null != context) {
            try {
                if (packageName.equals("com.hiveview.clear.manager")) {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName.trim());// 防止包名前后多了空格
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                } else {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName.trim());// 防止包名前后多了空格
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                Logger.e(TAG, " startAppByPackage error :  " + (e != null ? e.getMessage() : " msg is null."));
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "包名为空", Toast.LENGTH_SHORT).show();
        }
    }

}
