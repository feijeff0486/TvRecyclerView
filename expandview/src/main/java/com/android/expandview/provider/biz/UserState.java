package com.android.expandview.provider.biz;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.expandview.bean.QuickMarkEntity;
import com.android.expandview.bean.UserInfoForOutEntity;
import com.gogh.okrxretrofit.util.Logger;
import com.google.gson.Gson;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/18
 * @Description 单例用户状态工具类，这里包含所有用户相关操作逻辑，使用时先调用{@link #getInstance()}来获取实例
 * 1.获取用户信息使用{@link #getUserStatus()}和{@link #getUserInfo()}来获取
 * 2.当需要刷新用户信息时请调用{@link #notifyReFreshUserState(Context)}
 * 3.如果你需要接受数据刷新完毕的通知，这里我们提供了观察者模式，你需要让你的类继承{@link UserStatusWatcher}
 * 并在初始化的时候通过{@link #addWatcher(UserStatusWatcher)}方法将自身注册为观察者
 * 这样就你可以获取userStatusChanged()的方法回调
 */
public class UserState {
    private static final String TAG = UserState.class.getSimpleName();
    public final static String ACTION_START_PERSON = "com.hiveview.cloudscreen.user.UserLoginHomeActivity";
    /**
     * 1期用户中心的数据库名
     */
    private static final String AUTHORITIES1 = "content://HiveViewCloudUserAuthorities/TABLE_USER_INFO_DOMYSHOP";
    /**
     * 0期用户中心的vip数据库名
     */
    public static final String AUTHORITIES0_VIP = "content://HiveViewCloudUserAuthorities/TABLE_USER_VIP_INFO";
    /**
     * 0期用户中心的数据库名
     */
    public static final String AUTHORITIES0_USER = "content://HiveViewCloudUserAuthorities/TABLE_USER_INFO";
    /**
     * 0期用户中心爱奇艺信息数据库名
     */
    public static final String AUTHORITIES0_AQY = "content://HiveViewCloudUserAuthorities/TABLE_QIYI_VIP_INFO";
    public final static String ACTION_START_VIP_PACKAGER = "com.hiveview.cloudscreen.video.action.VIP_PACKAGER";
    /**
     * 手机端数据库表
     */
    private static final String GET_BaiDuPush_INFO = "GET_BaiDuPush_INFO";
    private static final String EXTRA_SOURCE = "com.hiveview.cloudscreen.source";
    /**
     * 用户id
     */
    private final String ID = "id";
    /**
     * 用户名
     */
    private final String USER_ACCONUT = "userAccount";
    /**
     * 登陆状态
     */
    private final String LOGIN_STATE = "loginState";

    /**
     * 用户中心程序版本号，用于兼容用户中心的各个版本
     */
    private String userAppCode;

    public String version;

    /**
     * 用于保存当前用户状态
     */
    private UserStatus userStatus = UserStatus.NOLOGIN;
    /**
     * 用户参数
     */
    private static UserInfo userInfo = new UserInfo();

    /**
     * 用于存放观察者，通过观察者模式通知用户数据变更
     */
    private CopyOnWriteArrayList<UserStatusWatcher> userStatusWatcherList = new CopyOnWriteArrayList<>();

    /**
     * 用户数据是否已经准备好了
     */
    private boolean isUserStatusReady = false;


    /**
     * 单例该类
     */
    private static class UserHolder {
        private static final UserState INSTANCE = new UserState();
    }

    private UserState() {
    }

    public static final UserState getInstance() {
        return UserHolder.INSTANCE;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 获取用户状态
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 获取用户信息
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @Author Spr_ypt
     * @Date 2017/5/5
     * @Description 根据爱奇艺会员期限判断是否还是爱奇艺会员
     */
    public boolean isQiyiVip() {
        try {
            int deadLine = Integer.parseInt(userInfo.deadLine);
            Log.d(TAG, "isQiyiVip deadLine=" + deadLine);
            Log.d(TAG, "isQiyiVip currentTimeMillis=" + System.currentTimeMillis());
            return System.currentTimeMillis() / 1000 < deadLine;
        } catch (Exception e) {
            Log.d(TAG, "isQiyiVip e=" + e.toString());
            return false;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 进行用户状态相关操作后调用，来重新获取用户状态
     */
    public void notifyReFreshUserState(Context context) {
        Logger.i(TAG, "notifyReFreshUserState");
        new AsyRefreshUserStateTask().execute(context);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 获取用户信息的线程
     */
    private class AsyRefreshUserStateTask extends AsyncTask<Context, String, UserInfo> {


        @Override
        protected UserInfo doInBackground(Context... params) {
            userAppCode = getVersionCode(params[0], "com.hiveview.cloudscreen.user");
            Log.i(TAG, "userAppCode=" + userAppCode);
            if ("".equals(userAppCode)) {
                return null;
            } else if ("1".equals(userAppCode)) {//0期用户中心程序，0期用户不存在未登录的情况
                return getUserInfoFrom0(params[0]);
            } else {//1期用户中心程序
                return getUserInfoFrom3(params[0]);
            }

        }

        @Override
        protected void onPostExecute(UserInfo userInfo) {
            if (null != userInfo && null != userInfo.isVip) {
                Logger.i(TAG, "userInfo=" + userInfo.toString());
                Logger.i(TAG, "myUserInfo=" + UserState.userInfo.toString());
                if (!userInfo.equals(UserState.userInfo)) {
                    UserState.userInfo = userInfo;
                    switch (userInfo.isVip) {
                        case "-1":
                            userStatus = UserStatus.NOLOGIN;
                            break;
                        case "0":
                            userStatus = UserStatus.NOMALUSER;
                            break;
                        case "1":
                            userStatus = UserStatus.VIPUSER;
                            break;
                        default:
                            if (userInfo.userId.equals("")) {
                                userStatus = UserStatus.NOLOGIN;
                            } else {
                                userStatus = UserStatus.NOMALUSER;
                            }
                            break;
                    }
                    if (userInfo.userId.equals("")) {
                        //拿不到用户数据的情况全部理解为未登录
                        userStatus = UserStatus.NOLOGIN;
                    }
                    notifyWatchers();
                } else {
                    // 用户信息无更新
                    noChanged();
                }
            } else {
                Logger.e(TAG, "com.hiveview.cloudscreen.user is not find!");
                if (null != UserState.userInfo) {
                    userStatus = UserStatus.NOLOGIN;
                    UserState.userInfo = null;
                    notifyWatchers();
                }

            }
            isUserStatusReady = true;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 从0期用户中心获取用户信息，0期不存在未登录的用户
     */
    private UserInfo getUserInfoFrom0(Context context) {
        UserInfo info = new UserInfo();

        try {
            ContentResolver cr = context.getContentResolver();
            Cursor cursorUser = cr.query(Uri.parse(AUTHORITIES0_USER), null, null, null, null);
            if (cursorUser != null && cursorUser.moveToFirst()) {
                info.userId = cursorUser.getString(cursorUser.getColumnIndex("id"));
                info.userAccount = cursorUser.getString(cursorUser.getColumnIndex("userAccount"));
                info.sn = cursorUser.getString(cursorUser.getColumnIndex("sn"));
                info.mac = cursorUser.getString(cursorUser.getColumnIndex("mac"));
                info.createTime = cursorUser.getString(cursorUser.getColumnIndex("createTime"));
                info.userPhone = cursorUser.getString(cursorUser.getColumnIndex("userPhone"));
                info.hasOpenId = cursorUser.getString(cursorUser.getColumnIndex("hasOpenId"));
            }
            if (null != cursorUser) {
                cursorUser.close();
            }
            Cursor cursorVip = cr.query(Uri.parse(AUTHORITIES0_VIP), null, null, null, null);
            if (cursorVip != null && cursorVip.moveToFirst()) {
                info.expiredDate = cursorVip.getString(cursorVip.getColumnIndex("expiredDate"));
                info.isVip = cursorVip.getString(cursorVip.getColumnIndex("isVip"));
                info.hasFreePackage = cursorVip.getString(cursorVip.getColumnIndex("hasFreePackage"));
                info.updateTime = cursorVip.getString(cursorVip.getColumnIndex("updateTime"));
                info.deviceCode = cursorVip.getString(cursorVip.getColumnIndex("deviceCode"));
            } else {
                info.isVip = "0";
            }
            if (null != cursorVip) {
                cursorVip.close();
            }
            Cursor cursorAQY = cr.query(Uri.parse(AUTHORITIES0_AQY), null, null, null, null);
            if (null != cursorAQY && cursorAQY.moveToFirst()) {
                info.deadLine = cursorAQY.getString(cursorAQY.getColumnIndex("deadline"));
            }
            if (null != cursorAQY) {
                cursorAQY.close();
            }
        } catch (Exception e) {
            Logger.e(TAG, e.toString());
        }
        return info;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 通过1期的用户中心获取用户信息
     */
    private UserInfo getUserInfoFrom1(Context context) {
        UserInfo info = new UserInfo();
        try {
            //核心代码
            ContentResolver resolver = context.getContentResolver();
            Uri mUri = Uri.parse(AUTHORITIES1);
            Bundle mUserInfoBundle = resolver.call(mUri, "METHOD_GET_USERINFO", null, null);

            if (null != mUserInfoBundle) {
                info.userId = mUserInfoBundle.getString("userId");
                Log.i("userid", info.userId);
                info.userAccount = mUserInfoBundle.getString("userAccount");
                info.isVip = mUserInfoBundle.getString("isVip");
                Log.i("isVip", info.isVip);
                info.isVisitor = mUserInfoBundle.getString("isVisitor");
                if ("1".equals(info.isVip)) {
                    info.expiredDate = mUserInfoBundle.getString("expiredDate");
                    info.hasFreePackage = mUserInfoBundle.getString("hasFreePackage");
                    info.updateTime = mUserInfoBundle.getString("updateTime");
                    info.deviceCode = mUserInfoBundle.getString("deviceCode");
                    info.mac = mUserInfoBundle.getString("mac");
                    info.sn = mUserInfoBundle.getString("sn");
                }
            }
        } catch (Exception e) {
            Logger.e(TAG, e.toString());
        }
        return info;
    }

    /**
     * @Author wangbei
     * @Date 2016/3/10
     * @Description 通过1期的用户中心获取用户信息
     */
    private UserInfo getUserInfoFrom3(Context context) {
        UserInfo info = new UserInfo();
        try {
            //核心代码
            ContentResolver resolver = context.getContentResolver();
            Uri mUri = Uri.parse(AUTHORITIES0_USER);
            Bundle mUserInfoBundle = resolver.call(mUri, "METHOD_GET_USERINFO", null, null);
            if (null != mUserInfoBundle) {
                String user_info = mUserInfoBundle.getString("user_info");
                UserInfoForOutEntity parseObject = new Gson().fromJson(user_info, UserInfoForOutEntity.class);
                if (null != parseObject && parseObject.getLoginState().equals("1")) {
                    info.userId = parseObject.getId();
                    Log.i("LoginActivity id:", info.userId);
                    info.userAccount = parseObject.getUserAccount();
                    info.isVip = parseObject.getIsVip();
                    info.expiredDate = parseObject.getExpiredDate();
                    info.hasFreePackage = parseObject.getHasFreePackage();
                    info.updateTime = parseObject.getVipUpdateTime();
                    info.loginState = parseObject.getLoginState();
                    info.accessToken = parseObject.getAccessToken();
                    info.refreshToken = parseObject.getRefreshToken();
                    info.baiduUserId = parseObject.getBaiduUserId();
                    info.channelId = parseObject.getChannelId();
                    info.userPhone = parseObject.getUserPhone();
                    info.hasOpenId = parseObject.getHasOpenId();
                } else {

                }
            }
        } catch (Exception e) {
            Logger.e(TAG, e.toString());
        }
        return info;
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 处理登录逻辑，弹出登录框
     */
    public void dealLogin(Context context) {
        if ("1".equals(userAppCode)) {
            try {
                Intent intent = new Intent();
                intent.setAction(ACTION_START_PERSON);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                Logger.e(TAG, "e=" + e.toString());
//                ToastUtil.showToast(context, context.getString(R.string.user_app_not_exist), Toast.LENGTH_SHORT);
            }
        } else {
            try {
                //为未登录状态
                Intent intent = new Intent();
                //com.hiveview.cloudscreen.user.HOME 为登录注册的主界面的action
                intent.setAction("com.hiveview.cloudscreen.user.HOME");
                //这个name = fromOtherApk 为一个标示是固定的 要写死哦。 后面的value值 代表贵公司产品的一个标示,不要为空哦。
                intent.putExtra("appSource", "CloudScreenVipVideo");
                context.startActivity(intent);
            } catch (Exception e) {
                Logger.e(TAG, "e=1期：" + e.toString());
//                ToastUtil.showToast(context, context.getString(R.string.user_app_not_exist), Toast.LENGTH_SHORT);
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/18
     * @Description 处理登录逻辑，弹出够买VIP框
     */
    public void becomeVip(Context context, String source) {
        Intent intent = new Intent(ACTION_START_VIP_PACKAGER);
        if (source != null) {
            intent.putExtra(EXTRA_SOURCE, source);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Log.i(TAG, "start vippackage");
    }

    /**
     * 获取指定应用的版本号
     *
     * @return
     */
    public String getVersionCode(Context ctx, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return "";
        }
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            Log.d(TAG, "versionName=" + pi.versionName);
            String[] vers = pi.versionName.split("\\.");
            version = vers[1];
            return vers[0];
        } catch (Exception e) {
            Logger.e(TAG, "Exception=" + e.toString());
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用户状态
     * NOLOGIN未登录，NOMALUSER普通用户，VIPUSER Vip用户
     */
    public static enum UserStatus {
        NOLOGIN, NOMALUSER, VIPUSER
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 设备信息内部类
     */
    public static class UserInfo {
        /*到期时间*/
        public String expiredDate = "";
        /**
         * 是否是vip,"-1"未登录，"0"非会员，"1"会员
         */
        public String isVip = "";
        /*是否领取了免费vip包*/
        public String hasFreePackage = "";
        /**/
        public String updateTime = "";
        /*用户id*/
        public String userId = "";
        /*设备码*/
        public String deviceCode = "";
        /**/
        public String mac = "";
        public String sn = "";
        /*用户名称*/
        public String userAccount = "";
        //是否访客
        public String isVisitor = "";
        //用戶状态
        public String loginState = "";
        //用户手机号
        public String userPhone = "";
        public String hasOpenId = "";
        // 创建日期
        public String createTime = "";
        //3.2 废弃
        public String accessToken = "";
        public String refreshToken = "";
        public String channelId = "";
        public String baiduUserId = "";

        //爱奇艺会员信息
        public String deadLine = "";

        @Override
        public String toString() {
            return "UserInfo{" +
                    "expiredDate='" + expiredDate + '\'' +
                    ", isVip='" + isVip + '\'' +
                    ", hasFreePackage='" + hasFreePackage + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", userId='" + userId + '\'' +
                    ", deviceCode='" + deviceCode + '\'' +
                    ", mac='" + mac + '\'' +
                    ", sn='" + sn + '\'' +
                    ", userAccount='" + userAccount + '\'' +
                    ", isVisitor='" + isVisitor + '\'' +
                    ", loginState='" + loginState + '\'' +
                    ", userPhone='" + userPhone + '\'' +
                    ", hasOpenId='" + hasOpenId + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", accessToken='" + accessToken + '\'' +
                    ", refreshToken='" + refreshToken + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", baiduUserId='" + baiduUserId + '\'' +
                    ", deadLine='" + deadLine + '\'' +
                    '}';
        }


        @Override
        public boolean equals(Object o) {
            if (o instanceof UserInfo) {
                Logger.d(TAG, "userInfo=" + o.toString());
                return this.toString().equals(o.toString());
            } else {
                return false;
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/2/20
     * @Description 判断用户数据是否就绪
     */
    public boolean isUserStatusReady() {
        return isUserStatusReady;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 添加观察者
     */
    public void addWatcher(UserStatusWatcher userStatusWatcher) {
        if (!userStatusWatcherList.contains(userStatusWatcher)) {
            userStatusWatcherList.add(userStatusWatcher);
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 移除观察者
     */
    public void removeWatcher(UserStatusWatcher userStatusWatcher) {
        userStatusWatcherList.remove(userStatusWatcher);
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 通知观察者的方法
     */
    public void notifyWatchers() {
        Log.i(TAG, "notifyWatchers");
        // 自动调用实际上是主题进行调用的
        for (UserStatusWatcher userStatusWatcher : userStatusWatcherList) {
            userStatusWatcher.userStatusChanged();
        }
    }

    public void noChanged() {
        for (UserStatusWatcher userStatusWatcher : userStatusWatcherList) {
            userStatusWatcher.userStatusNoChanged();
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/28
     * @Description 观察者接口
     */
    public interface UserStatusWatcher {
        public void userStatusChanged();

        public void userStatusNoChanged();

    }

    /**
     * 请求数据库拿到baiduUserId、channelId
     *
     * @param context
     * @return
     */
    public UserInfo createQuickmark(Context context) {
        UserInfo info = getUserInfo();
        try {
            //核心代码
            ContentResolver resolver = context.getContentResolver();
            Uri mUri = Uri.parse("content://HiveViewYunPushAuthorities/TABLE_BAIDUINFO_DAO");
            Bundle mUserInfoBundle = resolver.call(mUri, GET_BaiDuPush_INFO, null, null);
            if (null != mUserInfoBundle) {
                info.baiduUserId = mUserInfoBundle.getString("userId");
                info.channelId = mUserInfoBundle.getString("channelId");
            } else {
                ReciverQuickMark(context);
            }
        } catch (Exception e) {
            Logger.e(TAG, "Exception=" + e.toString());
            e.printStackTrace();
        }
        return info;
    }

    private void ReciverQuickMark(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.abel.action.broadcast");
        context.registerReceiver(new QuickMarkReciver(), intentFilter);
    }

    private class QuickMarkReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            UserInfo info = new UserInfo();
            String action = intent.getAction();
            if (action.equals("com.hiveview.baidupush.bind")) {
                info.baiduUserId = intent.getStringExtra("userId");
                info.channelId = intent.getStringExtra("channelId");
            }
        }
    }


    /**
     * 已废弃
     * 接收用户微信信息或者用户绑定手机号 的广播
     */
    public void GetUserInfo(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.hiveview.baidupush.passthrough.action");
        context.registerReceiver(new GetUserInfoReceiver(), intentFilter);
    }


    public class GetUserInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            QuickMarkEntity entity = new QuickMarkEntity();
            if (action.equals("com.hiveview.baidupush.passthrough.action")) {
                entity.msgType = intent.getStringExtra("msgType");
                Log.i(TAG, "广播：" + entity.msgType);
                if (userInfoInter != null && !entity.msgType.equals("14")) {
                    userInfoInter.notifal(entity);
                }
            }
        }
    }

    UserInfoInter userInfoInter;

    public interface UserInfoInter {
        void notifal(QuickMarkEntity entity);
    }

    public void setUserInfoInter(UserInfoInter userInfoInter) {
        this.userInfoInter = userInfoInter;
    }

}
