/*****************************************************************************
 * AndroidUtil.java
 * ****************************************************************************
 * Copyright © 2015 VLC authors, VideoLAN and VideoLabs
 * <p>
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package com.pchyyiyi.textureplay.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.pchyyiyi.textureplay.TextureApplication;

import java.util.List;
import java.util.Stack;

public class AndroidUtil {
    private static final String TAG="AndroidUtil";

    final static Stack<Activity> activityList = new Stack<Activity>();

    public static boolean isFroyoOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean isGingerbreadOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isHoneycombOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isHoneycombMr1OrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean isICSOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean isJellyBeanOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean isJellyBeanMR1OrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean isJellyBeanMR2OrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isLolliPopOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLolliPopMR1OrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static boolean isMarshmallowOrLater() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNougatOrLater() {//24
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isOreoOrLater() { //26
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    public static boolean isPieOrLater() { //29
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    public static boolean isQqqOrLater() {//29
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     */
    public static void next(Activity curActivity, Class<?> nextActivity) {
        next(curActivity, nextActivity, null, -1, -1);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param extras
     */
    public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras) {
        next(curActivity, nextActivity, extras, -1, -1);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param extras
     * @param reqCode
     */
    public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode) {
        next(curActivity, nextActivity, extras, reqCode, -1);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param flag
     */
    public static void next(Activity curActivity, Class<?> nextActivity, int flag) {
        next(curActivity, nextActivity, null, -1, flag);
    }

    /**
     * 跳转到下一个页面
     *
     * @param curActivity
     * @param nextActivity
     * @param extras
     * @param reqCode
     */
    public static void next(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode, int flag) {
        Intent intent = new Intent(curActivity, nextActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        if (flag != -1) {
            intent.setFlags(flag);
        }
        if (reqCode < 0) {
            curActivity.startActivity(intent);
        } else {
            curActivity.startActivityForResult(intent, reqCode);
        }
    }

    public static void next(Activity curActivity, Class<?> nextActivity,
                            String action, Bundle extras, int reqCode, int flag) {
        Intent intent = new Intent(curActivity, nextActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        if (null != action)
            intent.setAction(action);
        if (flag != -1) {
            intent.setFlags(flag);
        }
        if (reqCode < 0) {
            curActivity.startActivity(intent);
        } else {
            curActivity.startActivityForResult(intent, reqCode);
        }
    }

    /**
     * 返回到上一个页面
     *
     * @param curActivity
     */
    public static void goBack(Activity curActivity) {
        curActivity.finish();
    }

    /**
     * 返回到上一个页面并返回值
     *
     * @param curActivity
     * @param retCode
     * @param retData
     */
    public static void goBackWithResult(Activity curActivity, int retCode, Bundle retData) {
        Intent intent = null;
        intent = new Intent();
        if (null != retData) {
            intent.putExtras(retData);
        }
        curActivity.setResult(retCode, intent);
        curActivity.finish();
    }

    /**
     * 直接返回到指定的某个页面
     * 跳转后finish
     * @param curActivity
     * @param backActivity
     */
    public static void backActivityWithClearTop(Activity curActivity,
                                                     Class<?> backActivity) {
        backActivityWithClearTop(curActivity, backActivity, null, true);
    }

    /**
     * 直接返回到指定的某个页面
     *
     * @param curActivity
     * @param backActivity
     */
    public static void backActivityWithClearTop(Activity curActivity,
                                                Class<?> backActivity, boolean isFinish) {
        backActivityWithClearTop(curActivity, backActivity, null, isFinish);
    }


    /**
     * 直接返回到指定的某个页面
     *
     * @param curActivity
     * @param backActivity
     * @param extras
     */
    public static void backActivityWithClearTop(Activity curActivity,
                                                Class<?> backActivity, Bundle extras) {
        backActivityWithClearTop(curActivity, backActivity, extras, true);
    }


    /**
     * 直接返回到指定的某个页面
     *
     * @param curActivity
     * @param backActivity
     * @param extras
     */
    public static void backActivityWithClearTop(Activity curActivity,
                                                Class<?> backActivity, Bundle extras, boolean isFinish) {
        Intent intent = new Intent(curActivity, backActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (null != extras) {
            intent.putExtras(extras);
        }
        curActivity.startActivity(intent);
        if (isFinish)
            curActivity.finish();
    }

    /**
     * 把当前Activity放入栈中
     *
     * @param activity
     */
    public static void pushActivity(Activity activity) {
        activityList.push(activity);
    }

    /**
     * 把当前Activity移除
     *
     * @param activity
     */
    public static void popActivity(Activity activity) {
        if (activity != null) {
            if (activityList.contains(activity))
                activityList.remove(activity);
            if (activity.isFinishing())
                return;
            activity.finish();
        }
    }

    /**
     * 退出系统
     */
    public static void exit() {
        while (!activityList.isEmpty()) {
            popActivity(activityList.pop());
        }
        System.out.println("AndroidUtils System.exit(0);");
        System.exit(0);
    }

    /**
     * 关闭其他界面
     */
    public static void closeOtherActivitys() {
        while (!activityList.isEmpty()) {
            popActivity(activityList.pop());
        }
    }


    /**
     * 获取当前应用的版本名
     *
     * @return 当前应用的版本名
     */
    public static String getVersionName() {
        try {
            PackageManager manager = TextureApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    TextureApplication.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0.0";
        }
    }

    /**
     * 获取当前应用的版本号
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = TextureApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    TextureApplication.getInstance().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity) {
        startActivity(curActivity, nextActivity, null, null, false);
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity, boolean isFinish) {
        startActivity(curActivity, nextActivity, null, null, isFinish);
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity, String action, boolean isFinish) {
        startActivity(curActivity, nextActivity, null, action, isFinish);
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity, Bundle extras) {
        startActivity(curActivity, nextActivity, extras, null, false);
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity, Bundle extras, boolean isFinish) {
        startActivity(curActivity, nextActivity, extras, null, isFinish);
    }

    public static void startActivity(Activity curActivity, Class<?> nextActivity, Bundle extras, String action, boolean isFinish) {
        Intent intent = new Intent(curActivity, nextActivity);
        if (!TextUtils.isEmpty(action))
            intent.setAction(action);
        if (null != extras)
            intent.putExtras(extras);
        curActivity.startActivity(intent);
        if (isFinish)
            curActivity.finish();
    }

    public static void startActivityTop(Activity curActivity,
                                        Class<?> backActivity) {
        startActivityTop(curActivity, backActivity, null);
    }

    public static void startActivityTop(Activity curActivity,
                                        Class<?> backActivity, Bundle extras) {
        Intent intent = new Intent(curActivity, backActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (null != extras) {
            intent.putExtras(extras);
        }
        curActivity.startActivity(intent);
        curActivity.finish();
    }

    public static void startActivityForResult(Activity curActivity, Class<?> nextActivity, int reqCode) {
        startActivityForResult(curActivity, nextActivity, null, null, reqCode, false);
    }

    public static void startActivityForResult(Activity curActivity, Class<?> nextActivity, int reqCode,
                                              boolean isFinish) {
        startActivityForResult(curActivity, nextActivity, null, null, reqCode, isFinish);
    }

    public static void startActivityForResult(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode) {
        startActivityForResult(curActivity, nextActivity, extras, null, reqCode, false);
    }

    public static void startActivityForResult(Activity curActivity, Class<?> nextActivity, Bundle extras, int reqCode,
                                              boolean isFinish) {
        startActivityForResult(curActivity, nextActivity, extras, null, reqCode, isFinish);
    }

    public static void startActivityForResult(Activity curActivity, Class<?> nextActivity, String action, int reqCode,
                                              boolean isFinish) {
        startActivityForResult(curActivity, nextActivity, null, action, reqCode, isFinish);
    }

    public static void startActivityForResult(Activity curActivity, Class<?> nextActivity, Bundle extras, String action, int reqCode
            , boolean isFinish) {
        Intent intent = new Intent(curActivity, nextActivity);
        if (!TextUtils.isEmpty(action))
            intent.setAction(action);
        if (null != extras)
            intent.putExtras(extras);
        curActivity.startActivityForResult(intent, reqCode);
        if (isFinish)
            curActivity.finish();
    }

    public static void setActivityResult(Activity curActivity, int retCode, Bundle retData) {
        Intent intent = new Intent();
        if (null != retData) {
            intent.putExtras(retData);
        }
        curActivity.setResult(retCode, intent);
        curActivity.finish();
    }

    /**
     * 将隐式启动转换为显示启动
     * @param implicitIntent
     * @return
     */
    public static Intent getExplicitIntent(Intent implicitIntent) {
        return getExplicitIntent(TextureApplication.getAppContext(), implicitIntent);
    }

    /**
     * 将隐式启动转换为显示启动
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        AndroidLog.i(TAG, "packageName:"+packageName+", className:"+className);
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

}
