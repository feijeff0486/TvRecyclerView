package com.gogh.okrxretrofit.util;

import android.util.Log;

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: output debug info.</p>
 * <p> Created by <b>gaoxiaofeng</b> on 6/5/2017. </p>
 * <p> ChangeLog: </p>
 * <li> gaoxiaofeng on 08/15/2017 do fisrt create. </li>
 */

public class Logger {

    private static boolean DEBUG = false;
    private static boolean HTTP_DEBUG = false;

    public Logger() {
    }

    public static void setDebugMode(boolean debudMode) {
        DEBUG = debudMode;
    }

    /**
     * @param tag
     * @param msg
     * @author gaoxiaofeng
     */
    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg
     * @param throwable
     * @author gaoxiaofeng
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.v(tag, msg, throwable);
        }
    }

    /**
     * @param tag
     * @param msg
     * @author gaoxiaofeng
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg
     * @param throwable
     * @author gaoxiaofeng
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.d(tag, msg, throwable);
        }
    }

    /**
     * @param tag
     * @param msg
     * @author gaoxiaofeng
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg
     * @param throwable
     * @author gaoxiaofeng
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.i(tag, msg, throwable);
        }
    }

    /**
     * @param tag
     * @param msg
     * @author gaoxiaofeng
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg
     * @param throwable
     * @author gaoxiaofeng
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.w(tag, msg, throwable);
        }
    }

    /**
     * @param tag
     * @param msg
     * @author gaoxiaofeng
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg
     * @param throwable
     * @author gaoxiaofeng
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (DEBUG) {
            Log.e(tag, msg, throwable);
        }
    }

    public static void setHttpDebugModel(boolean httpModel) {
        HTTP_DEBUG = httpModel;
    }

    public static void debug(String tag, String msg) {
        if (HTTP_DEBUG) {
            Log.d(tag, msg);
        }
    }

}
