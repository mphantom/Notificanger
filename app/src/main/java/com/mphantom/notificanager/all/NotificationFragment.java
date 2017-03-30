package com.mphantom.notificanager.all;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mphantom.notificanager.OnRecyclerViewListener;
import com.mphantom.notificanager.R;
import com.mphantom.notificanager.utils.AppInfoUtil;
import com.mphantom.realmhelper.NotificationModel;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by mphantom on 17-3-24.
 */
public class NotificationFragment extends Fragment implements OnRecyclerViewListener {
    @BindView(R.id.recyclerView)
    RecyclerView rvNotify;
    NotifyAdapter adapter;
    Realm realm;
    public NotificationFragment() {
    }

//    public String getTagString() {
//        return this.getClass().getSimpleName();
//    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<NotificationModel> result = realm.where(NotificationModel.class)
                .findAllSorted("postTime", Sort.DESCENDING);
        adapter = new NotifyAdapter(getContext(), result, true);
        adapter.setOnItemClickListener(this);
        rvNotify.setAdapter(adapter);
        rvNotify.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void OnItemClick(View view, int position) {
        String intentDescription = adapter.getItem(position).getIntentUri();
        if (!TextUtils.isEmpty(intentDescription) && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Intent intent = null;
            try {
                intent = Intent.parseUri(intentDescription, 0);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (intent != null) {
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    getContext().sendBroadcast(intent);
                } catch (SecurityException e) {
//                    e.printStackTrace();
                    AppInfoUtil.startApplicationWithPackageName(getContext(),
                            adapter.getItem(position).getPackageName());
                }
            }
        } else {
            AppInfoUtil.startApplicationWithPackageName(getContext(),
                    adapter.getItem(position).getPackageName());
        }
    }
}

