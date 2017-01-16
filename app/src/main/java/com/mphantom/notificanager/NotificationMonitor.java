package com.mphantom.notificanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.mphantom.realmhelper.NotificationModel;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by wushaorong on 16-10-25.
 */
public class NotificationMonitor extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        setNotification();

        Log.d(TAG, " on create the thread is " + Thread.currentThread().getId());
    }

    //设置通知栏常驻
    private void setNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("打开影子")
                .setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                .build();
        notificationManager.notify(R.string.app_name, notification);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        Log.d(TAG, " on posted the thread is " + Thread.currentThread().getId());
//        sbn.getId();
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
        notifi.setTitle(bundle.getString(Notification.EXTRA_TITLE));
        notifi.setText(bundle.getString(Notification.EXTRA_TEXT));
        notifi.setInfoText(bundle.getString(Notification.EXTRA_INFO_TEXT));
        notifi.setSubText(bundle.getString(Notification.EXTRA_SUB_TEXT));
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "sbninfo==" + sbn.toString());
        Log.d(TAG, "notifiyBundle==" + bundle.toString());
        if (!new ArrayList<>().contains(sbn.getPackageName()))
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
