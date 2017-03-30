package com.mphantom.notificanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mphantom.notificanager.utils.AppInfoUtil;
import com.mphantom.notificanager.utils.Sharedutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by mphantom on 17-3-24.
 */
public class IgnoreFragment extends Fragment {
    private static final String TAG = IgnoreFragment.class.getName();
    @BindView(R.id.rv_ignoreApp)
    RecyclerView rvIgnores;
    @BindView(R.id.pbWait)
    ContentLoadingProgressBar pbWait;

    @BindView(R.id.tv_tip)
    TextView tvTip;
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
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> e) throws Exception {
                String strIgnores = Sharedutils.getInstance(App.getInstance()).getString("ignore");
                ArrayList<String> ignores = TextUtils.isEmpty(strIgnores) ?
                        new ArrayList<String>() : new ArrayList<>(Arrays.asList(strIgnores.split(":")));
                e.onNext(ignores);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> list) throws Exception {
                        if (list.size() > 0) {
                            adapter.updateDate(AppInfoUtil.getAppListWithPackageName(getContext(), list));
                            pbWait.setVisibility(View.GONE);
                            rvIgnores.setVisibility(View.VISIBLE);
                        } else {
                            pbWait.setVisibility(View.GONE);
                            tvTip.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
