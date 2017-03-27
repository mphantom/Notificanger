package com.mphantom.notificanager;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.mphantom.realmhelper.RealmHelper;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

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
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
//        Stetho.initializeWithDefaults(this);
    }
}
