package com.mphantom.notificanager;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mphantom.realmhelper.RealmHelper;

/**
 * Created by mphantom on 17/1/12.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RealmHelper.init(this);
        instance = this;
        Stetho.initializeWithDefaults(this);
    }
}
