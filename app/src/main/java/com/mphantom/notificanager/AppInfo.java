package com.mphantom.notificanager;

import android.graphics.drawable.Drawable;

/**
 * Created by mphantom on 17/1/16.
 */

public class AppInfo {
    private String appName;
    private Drawable appIcon;
    private String packageName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\'' +
                ", appIcon=" + appIcon +
                ", appName='" + appName + '\'' +
                '}';
    }
}

