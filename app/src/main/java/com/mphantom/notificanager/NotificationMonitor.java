package com.mphantom.notificanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.mphantom.realmhelper.NotificationModel;

import io.realm.Realm;

/**
 * Created by wushaorong on 16-10-25.
 */
public class NotificationMonitor extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();
    //    private NLServiceReceiver nlservicereciver;
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
//        nlservicereciver = new NLServiceReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.kpbird.nlsexample.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
//        registerReceiver(nlservicereciver, filter);
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
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        NotificationModel notifi = realm.createObject(NotificationModel.class);
        notifi.setId(sbn.getId());
        notifi.setPostTime(sbn.getPostTime());
        notifi.setPackageName(sbn.getPackageName());
        notifi.setOngoing(sbn.isOngoing());
        notifi.setClearable(sbn.isClearable());
        realm.commitTransaction();
        realm.close();
        Log.d(TAG, "sbninfo==" + sbn.toString());
        Log.d(TAG, "notifiyBundle==" + sbn.getNotification().extras.toString());
//        cancelAllNotifications();
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
