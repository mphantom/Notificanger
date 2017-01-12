package com.mphantom.notificanger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
        realm = Realm.getDefaultInstance();

    }

    //设置通知栏常驻
    private void setNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("返回")
                .setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))
                .build();
        notificationManager.notify(R.string.app_name, notification);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(nlservicereciver);
        realm.close();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
//        sbn.getId();
        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        realm.beginTransaction();
        NotificationModel notifi = realm.createObject(NotificationModel.class);
        notifi.setId(sbn.getId());
        notifi.setPostTime(sbn.getPostTime());
        notifi.setPackageName(sbn.getPackageName());
        notifi.setOngoing(sbn.isOngoing());
        notifi.setClearable(sbn.isClearable());
        realm.commitTransaction();
//        Intent i = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
//        i.putExtra("notification_event", "onNotificationPosted :" + sbn.getPackageName() + "\n");
//        sendBroadcast(i);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
//        Intent i = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
//        i.putExtra("notification_event", "onNotificationRemoved :" + sbn.getPackageName() + "\n");
//
//        sendBroadcast(i);
    }

//    class NLServiceReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getStringExtra("command").equals("clearall")) {
//                NotificationMonitor.this.cancelAllNotifications();
//            } else if (intent.getStringExtra("command").equals("list")) {
//                Intent i1 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
//                i1.putExtra("notification_event", "=====================");
//                sendBroadcast(i1);
//                int i = 1;
//                for (StatusBarNotification sbn : NotificationMonitor.this.getActiveNotifications()) {
//                    Intent i2 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
//                    i2.putExtra("notification_event", i + " " + sbn.getPackageName() + "\n");
//                    sendBroadcast(i2);
//                    i++;
//                }
//                Intent i3 = new Intent("com.kpbird.nlsexample.NOTIFICATION_LISTENER_EXAMPLE");
//                i3.putExtra("notification_event", "===== Notification List ====");
//                sendBroadcast(i3);
//
//            }
//
//        }
//    }
}
