package com.mphantom.notificanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mphantom.realmhelper.NotificationModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by mphantom on 17/1/12.
 */

public class NotifyAdapter extends RealmRecyclerViewAdapter<NotificationModel, NotifyAdapter.ViewHolder> {

    private DateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public NotifyAdapter(@NonNull Context context, @Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_notify, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String postTime =sdf.format(new Date(getItem(position).getPostTime()));
        holder.tvPostTime.setText(postTime);
        holder.tvTitle.setText(getItem(position).getTitle());
        holder.tvContent.setText(getItem(position).getText());
        holder.tvContentInfo.setText(getItem(position).getInfoText());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_post_time)
        TextView tvPostTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_contentInfo)
        TextView tvContentInfo;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
