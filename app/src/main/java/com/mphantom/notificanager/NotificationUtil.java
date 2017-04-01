package com.mphantom.notificanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.mphantom.notificanager.utils.Sharedutils;

import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by mphantom on 2017/1/25.
 */

public class NotificationUtil implements SharedPreferences.OnSharedPreferenceChangeListener {
    private List<String> ignores;
    private List<String> filters;

    public NotificationUtil(Context context) {
        String ignoreStr = Sharedutils.getInstance(context).getString("ignore");
        if (!TextUtils.isEmpty(ignoreStr)) {
            ignores = Arrays.asList(ignoreStr.split(":"));
        } else {
            ignores = null;
        }

        String filterStr = Sharedutils.getInstance(context).getString("filter");
        if (!TextUtils.isEmpty(filterStr)) {
            filters = Arrays.asList(filterStr.split(":"));
        } else {
            filters = null;
        }

    }

    public List<String> getIgnores() {
        return ignores;
    }

    public List<String> getFilters() {
        return filters;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "shared Preference change,key==" + key);
        if ("ignore".equals(key)) {
            String ignoreStr = sharedPreferences.getString("ignore", "");
            if (!TextUtils.isEmpty(ignoreStr)) {
                ignores = Arrays.asList(ignoreStr.split(":"));
            } else {
                ignores = null;
            }
        }
    }
}
