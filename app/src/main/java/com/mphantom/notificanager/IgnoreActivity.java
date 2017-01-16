package com.mphantom.notificanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IgnoreActivity extends AppCompatActivity {
    private final String TAG = IgnoreActivity.class.getSimpleName();
    @BindView(R.id.rv_app)
    RecyclerView rvApp;

    IgnoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignore);
        ButterKnife.bind(this);
        rvApp.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IgnoreAdapter(getApplist());
        rvApp.setAdapter(adapter);
//        getApplist();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, IgnoreActivity.class));
    }

    private List getApplist() {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<PackageInfo> packlist = pm.getInstalledPackages(0);
        Log.d(TAG, "packlist size==" + packlist.size());
        for (PackageInfo info : packlist) {
            AppInfo appinfo = new AppInfo();
            appinfo.setAppName(info.applicationInfo.loadLabel(pm).toString());
            appinfo.setAppIcon(info.applicationInfo.loadIcon(pm));
            appinfo.setPackageName(info.packageName);
            appList.add(appinfo);
            Log.d(TAG, "appinfo ==" + appinfo.toString());

//            Resources res = getPackageManager().getResourcesForApplication("com.example.foo")
        }
        return appList;

    }
}
