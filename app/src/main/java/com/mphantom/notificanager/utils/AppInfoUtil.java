package com.mphantom.notificanager.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.mphantom.notificanager.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by mphantom on 2017/3/24.
 */

public class AppInfoUtil {


    public static void startApplicationWithPackageName(Context context, String packageName) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return;
        }
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageInfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveInfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        try {
            ResolveInfo resolveinfo = resolveInfoList.iterator().next();
            if (resolveinfo != null) {
                // packagename = 参数packname
                String packName = resolveinfo.activityInfo.packageName;
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
                String className = resolveinfo.activityInfo.name;
                // LAUNCHER Intent
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 设置ComponentName参数1:packagename参数2:MainActivity路径
                ComponentName cn = new ComponentName(packName, className);

                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (NoSuchElementException e) {
        }
    }

    public static List<AppInfo> getAllAppList(Context context) {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageList = pm.getInstalledPackages(0);
        for (PackageInfo info : packageList) {
            AppInfo appinfo = new AppInfo();
            appinfo.setAppName(info.applicationInfo.loadLabel(pm).toString());
            appinfo.setAppIcon(info.applicationInfo.loadIcon(pm));
            appinfo.setPackageName(info.packageName);
            appList.add(appinfo);
        }
        Collections.sort(appList, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                return o1.getAppName().compareTo(o2.getAppName());
            }
        });
        return appList;

    }

    public static List<AppInfo> getAppListWithPackageName(Context context, List<String> packageNames) {
        List<AppInfo> appList = new ArrayList<>();
        List<PackageInfo> packageInfos = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        for (String packageName : packageNames) {
            try {
                PackageInfo packageinfo = context.getPackageManager().getPackageInfo(packageName, 0);
                packageInfos.add(packageinfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        for (PackageInfo info : packageInfos) {
            AppInfo appinfo = new AppInfo();
            appinfo.setAppName(info.applicationInfo.loadLabel(pm).toString());
            appinfo.setAppIcon(info.applicationInfo.loadIcon(pm));
            appinfo.setPackageName(info.packageName);
            appList.add(appinfo);
        }
        Collections.sort(appList, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo o1, AppInfo o2) {
                return o1.getAppName().compareTo(o2.getAppName());
            }
        });
        return appList;
    }
}
