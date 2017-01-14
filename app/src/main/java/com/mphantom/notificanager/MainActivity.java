package com.mphantom.notificanager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mphantom.notificanager.utils.NotificationUtils;
import com.mphantom.realmhelper.NotificationModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView rvNotify;


    NotifyAdapter adapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!NotificationUtils.isEnabled(this)) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }
        realm = Realm.getDefaultInstance();
        initViews();
    }

    private void initViews() {
        RealmResults<NotificationModel> result = realm.where(NotificationModel.class)
                .findAll();
        adapter = new NotifyAdapter(this, result, true);
        rvNotify.setAdapter(adapter);
        rvNotify.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @OnClick(R.id.btn_add_ignore)
    void addIgnore(View view) {
        IgnoreActivity.start(this);
    }
}

