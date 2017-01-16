package com.mphantom.notificanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mphantom on 17/1/16.
 */

public class IgnoreAdapter extends RecyclerView.Adapter<IgnoreAdapter.ViewHolder> {
    private List<AppInfo> appInfos;

    public IgnoreAdapter(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ignore, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivIcon.setImageDrawable(appInfos.get(position).getAppIcon());
        holder.tvName.setText(appInfos.get(position).getAppName());
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


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
