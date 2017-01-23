package com.mphantom.notificanager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphantom.notificanager.utils.Sharedutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphantom on 17/1/16.
 */

public class IgnoreAdapter extends RecyclerView.Adapter<IgnoreAdapter.ViewHolder> {
    private List<AppInfo> appInfos;
    private List<String> ignores;

    public IgnoreAdapter(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
        String str = Sharedutils.getInstance(App.getInstance()).getString("ignore");
        if ("".equals(str)) {
            ignores = new ArrayList<>();
        } else {
            ignores = new ArrayList<>(Arrays.asList(str.split(":")));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ignore, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.ivIcon.setImageDrawable(appInfos.get(position).getAppIcon());
        holder.tvName.setText(appInfos.get(position).getAppName());
        if (ignores.contains(appInfos.get(position).getPackageName())) {
            holder.cbIgnore.setChecked(true);
        } else {
            holder.cbIgnore.setChecked(false);
        }
        holder.cbIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    if (((CheckBox) v).isChecked()) {
                        ignores.add(appInfos.get(position).getPackageName());
                    } else {
                        ignores.remove(appInfos.get(position).getPackageName());
                    }
                    Log.d(IgnoreAdapter.class.getSimpleName(), "the ignores size=" + ignores.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.cb_ignore)
        CheckBox cbIgnore;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateDate(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
        notifyDataSetChanged();
    }

    public List<String> getIgnores() {
        return ignores;
    }
}
