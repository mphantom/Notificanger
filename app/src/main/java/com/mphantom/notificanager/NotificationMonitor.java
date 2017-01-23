package com.mphantom.notificanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;

import com.mphantom.notificanager.utils.Sharedutils;
import com.mphantom.realmhelper.NotificationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;

/**
 * Created by wushaorong on 16-10-25.
 */
public class NotificationMonitor extends NotificationListenerService implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String TAG = this.getClass().getSimpleName();
    private List<String> ignores;

    @Override
    public void onCreate() {
        super.onCreate();
        setNotification();
        ignores = new ArrayList<>();
        String ignoreStr = Sharedutils.getInstance(this).getString("ignore");
        if (!TextUtils.isEmpty(ignoreStr)) {
            ignores = Arrays.asList(ignoreStr.split(":"));
        }
        Sharedutils.getInstance(this).addListener(this);
        Log.d(TAG, " on create the thread is " + Thread.currentThread().getId());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "shared Preference change,key==" + key);
        if ("ignore".equals(key)) {
            String ignoreStr = sharedPreferences.getString("ignore", "");
            if (!TextUtils.isEmpty(ignoreStr)) {
                ignores = Arrays.asList(ignoreStr.split(":"));
            }
        }
    }

    //设置通知栏常驻
    private void setNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("正在运行")
                .setContentText("保持通知栏清洁")
                .setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                .build();
        notificationManager.notify(R.string.app_name, notification);
    }


    @Override
    public void onDestroy() {
        Sharedutils.getInstance(this).removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        Log.d(TAG, " on posted the thread is " + Thread.currentThread().getId());
        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Notification temp = sbn.getNotification();
        Bundle bundle = temp.extras;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        NotificationModel notifi = realm.createObject(NotificationModel.class);
        notifi.setId(sbn.getId());
        notifi.setPostTime(sbn.getPostTime());
        notifi.setPackageName(sbn.getPackageName());
        notifi.setOngoing(sbn.isOngoing());
        notifi.setClearable(sbn.isClearable());
        notifi.setTickerText(temp.tickerText.toString());
        notifi.setTitle(bundle.getString(Notification.EXTRA_TITLE));
        notifi.setText(bundle.getString(Notification.EXTRA_TEXT));
        notifi.setInfoText(bundle.getString(Notification.EXTRA_INFO_TEXT));
        notifi.setSubText(bundle.getString(Notification.EXTRA_SUB_TEXT));
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "sbninfo==" + sbn.toString());
        Log.d(TAG, "notifiyBundle==" + bundle.toString());
        if (ignores == null) {
            Log.d(TAG, "the ignore is null");
        } else {
            Log.d(TAG, "the ignore is not null");
        }
        for (String s : ignores) {
            Log.d(TAG, "the ignore is" + s);
        }
        Log.d(TAG, "the receiver packageName is" + sbn.getPackageName());
        if (!ignores.contains(sbn.getPackageName()))
            cancelNotification(sbn);

    }

    private void cancelNotification(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cancelNotification(sbn.getKey());
        } else {
            cancelNotification(sbn.getPackageName(), sbn.getTag(), sbn.getId());
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
    }

}
