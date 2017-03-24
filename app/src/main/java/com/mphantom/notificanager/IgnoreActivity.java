package com.mphantom.notificanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mphantom.notificanager.utils.AppInfoUtil;
import com.mphantom.notificanager.utils.Sharedutils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IgnoreActivity extends AppCompatActivity {
    private final String TAG = IgnoreActivity.class.getSimpleName();
    @BindView(R.id.rv_app)
    RecyclerView rvApp;

    @BindView(R.id.pbWait)
    ContentLoadingProgressBar pbWait;

    IgnoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ignore);
        ButterKnife.bind(this);
        rvApp.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IgnoreAdapter(new ArrayList<AppInfo>());
        rvApp.setAdapter(adapter);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, IgnoreActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateDate(AppInfoUtil.getAllAppList(this));
        pbWait.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        List<String> list = adapter.getIgnores();
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(":");
        }
        Sharedutils.getInstance(App.getInstance()).saveString("ignore", sb.toString());
        super.onDestroy();
    }


}
