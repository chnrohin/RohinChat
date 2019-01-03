/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.chnrohin.rohinchat.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.chnrohin.rohinchat.R;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public final class PermissionUtils {

    private static final String TAG = "PermissionUtils";

    private static int requestCode;

    private static PermissionUtils sPermission;

    private static boolean initialized = false;

    private static final boolean API_MORE_THAN_M = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

    private String permissionInfo = "";

//    /**
//     * 定位权限.
//     */
//    public static final List<String> permissionsLocationList = Collections.unmodifiableList(new ArrayList<String>() {
//        {
//            add(Manifest.permission.ACCESS_FINE_LOCATION);
//            add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//    });
//
//    /**
//     * 存储权限
//     */
//    public static final List<String> permissionStorageList = Collections.unmodifiableList(new ArrayList<String>() {
//        {
//            add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//    });
//
//    /**
//     * 读取权限
//     */
//    public static final List<String> permissionReadList = Collections.unmodifiableList(new ArrayList<String>() {
//        {
//            add(Manifest.permission.READ_PHONE_STATE);
//            add(Manifest.permission.READ_CONTACTS);
//        }
//    });
//
//    /**
//     * 写入权限
//     */
//    public static final List<String> permissionWriteList = Collections.unmodifiableList(new ArrayList<String>() {
//        {
//            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//    });

    private static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    private static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;

    private static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static void setSdkPermissionRequestCode(int requestCode) {
        PermissionUtils.requestCode = requestCode;
    }

    public int getRequestcode() {
        return requestCode;
    }

    public String getInfo() {
        return permissionInfo;
    }

    private PermissionUtils() {
        if (initialized) {
            throw new AssertionError("禁止通过构造器实例化此工具类！");
        }
        initialized = true;
    }

    public static PermissionUtils getInstance(int requestCode) {
        synchronized (PermissionUtils.class) {
            if (sPermission == null) {
                sPermission = new PermissionUtils();
                LogUtils.d(TAG + " initialized.");
            }
        }
        setSdkPermissionRequestCode(requestCode);
        return sPermission;
    }

    public void getDefaultPermission(Activity activity) {
        ArrayList<String> permissionList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 必须权限
            if (checkSelfPermission(activity, READ_PHONE_STATE)) {
                permissionList.add(READ_PHONE_STATE);
            }
            if (checkSelfPermission(activity, READ_CONTACTS)) {
                permissionList.add(Manifest.permission.READ_CONTACTS);
            }
            if (checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            // 非必须权限
            if (addPermission(activity, permissionList, ACCESS_FINE_LOCATION)) {
                permissionInfo += ACCESS_FINE_LOCATION + " 被拒/deny \n";
            }
            if (addPermission(activity, permissionList, ACCESS_COARSE_LOCATION)) {
                permissionInfo += ACCESS_COARSE_LOCATION + " 被拒/deny \n";
            }
            // 申请权限
            if (permissionList.size() > 0) {
                requestPermissions(activity, permissionList, requestCode);
            }
        }
    }

    /**
     *
     * @param activity
     * @param permissionList
     * @param permission
     * @return
     */
    private boolean addPermission(Activity activity,
                                  List<String> permissionList,
                                  String permission) {
            if (checkSelfPermission(activity, permission)) {
                return true;
            } else {
                if (shouldShowRequestPermissionRationale(activity, permission)) {
                    return true;
                } else {
                    permissionList.add(permission);
                    return false;
                }
            }
    }

    /**
     * 检查应用是否具有指定权限
     * @param activity 要检查的页面
     * @param permission 要检查的权限
     * @return 是否具有权限
     */
    private static boolean checkSelfPermission(Activity activity, String permission) {
        if (API_MORE_THAN_M) {
            return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private static boolean shouldShowRequestPermissionRationale(Activity activity,
                                                                String permission) {
        if (API_MORE_THAN_M) {
            return activity.shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    /**
     * if = true - 6.0以上版本 - 动态请求权限
     * if = false - 6.0以下版本 - 请求权限
     */
    private void requestPermissions(Activity permissionActivity,
                                    ArrayList<String> permissionList,
                                    int requestCode) {
        if (API_MORE_THAN_M) {
            final int mRequestCode = requestCode;
            final Activity mActivity = permissionActivity;
            final ArrayList<String> mPermissionList = permissionList;
            StringBuilder explain = new StringBuilder();
            AlertDialog.Builder alertDialog
                    = new AlertDialog.Builder(permissionActivity, R.style.AlertDialog_Base);
            for (String permission : permissionList) {
                explain.append(getPermissionExplain(permission));
                explain.append("\n");
            }
            alertDialog.setTitle(explain.toString());
            alertDialog.setNegativeButton(R.string.dialog_permission_negative, ((dialog, which) -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mActivity.requestPermissions(mPermissionList.toArray(new String[mPermissionList.size()]), mRequestCode);
                }
            }));
            alertDialog.setPositiveButton(R.string.dialog_permission_positive, ((dialog, which) -> mActivity.finish()));
        } else {
            //6.0版本以下申请权限操作
        }
    }

    /**
     * 自定义弹窗申请权限
     */
    public static void showPromptDialog() {

    }

//
//  -----------------------------------------    分割线    -----------------------------------------
//

    private static String getPermissionExplain(String permission) {
        String explain = "";
        HashMap<String, String> permissionDetail = new HashMap<>();
        permissionDetail.put(Manifest.permission.ACCESS_FINE_LOCATION
                , "获取定位权限以通过GPS定位获取精确位置(注:仅用于本地定位)");
        permissionDetail.put(Manifest.permission.ACCESS_COARSE_LOCATION
                , "获取定位权限以通过CellID或WiFi热点获取粗略的位置");
        permissionDetail.put(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , "获取写入权限以存储位置、软件用户设置等信息");
        permissionDetail.put(Manifest.permission.READ_PHONE_STATE
                , "获取手机信息权限通过IMEI码以绑定帐号，提升安全性");
        permissionDetail.put(Manifest.permission.READ_CONTACTS
                , "获取读取联系人权限通过特征码识别并推荐通讯录好友(注:不会保存联系人信息)");
        for (String key : permissionDetail.keySet()) {
            if (key.equals(permission)) {
                explain = permissionDetail.get(key);
            }
        }
        return explain;
    }

    public static void onRequestPermissionResult(int requestCode,
                                                 @NonNull String[] permissions,
                                                 @NonNull int[] grantResults) {
        StringBuilder msg = new StringBuilder();
        int length = grantResults.length;
        for (int i = 0; i < length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                msg.append("申请\"");
                msg.append(permissions[i]);
                msg.append("\"权限成功！RequestCode:");
                msg.append(requestCode);
            } else {
                msg.append("申请\"");
                msg.append(permissions[i]);
                msg.append("\"权限失败！RequestCode:");
                msg.append(requestCode);
            }
            LogUtils.d(msg.toString());
        }
    }

}
