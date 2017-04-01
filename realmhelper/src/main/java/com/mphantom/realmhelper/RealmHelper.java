package com.mphantom.realmhelper;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by mphantom on 17/1/10.
 */

public class RealmHelper {
    public static void init(Context context) {
        Realm.init(context);
    }
}
