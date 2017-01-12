package com.mphantom.notificanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mphantom.realmhelper.NotificationModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by mphantom on 17/1/12.
 */

public class NotifyAdapter extends RealmRecyclerViewAdapter<NotificationModel, NotifyAdapter.ViewHolder> {
    public NotifyAdapter(@NonNull Context context, @Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_notify, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPostTime.setText("消息时间："+getItem(position).getPostTime());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_post_time)
        TextView tvPostTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
