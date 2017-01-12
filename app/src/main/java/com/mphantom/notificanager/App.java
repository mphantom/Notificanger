package com.mphantom.notificanager;

import android.app.Application;

import com.mphantom.realmhelper.RealmHelper;

/**
 * Created by mphantom on 17/1/12.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmHelper.init(this);
    }
}
