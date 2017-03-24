package com.mphantom.notificanager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mphantom.notificanager.utils.NotificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
//    @BindView(R.id.recyclerView)
//    RecyclerView rvNotify;

    @BindView(R.id.bnv_tab)
    BottomNavigationView bnvTab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (!NotificationUtils.isEnabled(this)) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        }

        bnvTab.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_today:
                                currentFragment = 0;
                                changeCurrentFragment(NotificationFragment.newInstance());
                                invalidateOptionsMenu();
                                break;
                            case R.id.menu_all:
                                currentFragment = 1;
                                invalidateOptionsMenu();
                                break;
                            case R.id.menu_ignore:
                                currentFragment = 2;
                                changeCurrentFragment(IgnoreFragment.newInstance());
                                invalidateOptionsMenu();
                                break;
                        }
                        return true;
                    }
                }
        );
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                IgnoreActivity.start(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        switch (currentFragment) {
            case 0:
                menu.findItem(R.id.menu_add).setVisible(false);
                break;
            case 1:
                menu.findItem(R.id.menu_add).setVisible(false);
                break;
            case 2:
                menu.findItem(R.id.menu_add).setVisible(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCurrentFragment(NotificationFragment.newInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void changeCurrentFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_contain, fragment);
        transaction.commit();
    }

//    @OnClick(R.id.btn_add_ignore)
//    void addIgnore(View view) {
//        IgnoreActivity.start(this);
//    }
//
//    @OnClick(R.id.btnFilter)
//    void filter(View view) {
//        FilterActivity.start(this);
//    }
}

