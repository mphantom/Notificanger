package com.mphantom.notificanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mphantom.notificanager.utils.AppInfoUtil;
import com.mphantom.notificanager.utils.Sharedutils;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by mphantom on 17-3-24.
 */
public class IgnoreFragment extends Fragment {
    @BindView(R.id.rv_ignoreApp)
    RecyclerView rvIgnores;
    @BindView(R.id.pbWait)
    ContentLoadingProgressBar pbWait;

    IgnoreAdapter adapter;

    public IgnoreFragment() {
    }

    public static IgnoreFragment newInstance() {
        IgnoreFragment fragment = new IgnoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ignore, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter = new IgnoreAdapter(new ArrayList<AppInfo>());
        rvIgnores.setLayoutManager(new LinearLayoutManager(getContext()));
        rvIgnores.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        String strIgnores = Sharedutils.getInstance(App.getInstance()).getString("ignore");
        ArrayList<String> ignores = new ArrayList<>(Arrays.asList(strIgnores.split(":")));
        adapter.updateDate(AppInfoUtil.getAppListWithPackageName(getContext(), ignores));
        pbWait.setVisibility(View.GONE);
    }
}
