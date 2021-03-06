package com.mphantom.notificanager.today;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mphantom.notificanager.OnRecyclerViewListener;
import com.mphantom.notificanager.R;
import com.mphantom.realmhelper.NotificationModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import static android.content.pm.PackageManager.GET_META_DATA;

/**
 * Created by mphantom on 17/1/12.
 */

public class TodayAdapter extends RealmRecyclerViewAdapter<NotificationModel, TodayAdapter.ViewHolder> {

    private DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private PackageManager pm;
    private OnRecyclerViewListener onClickListener;


    public TodayAdapter(@NonNull Context context, @Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(context, data, autoUpdate);
        pm = context.getPackageManager();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_today, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String postTime = sdf.format(new Date(getItem(position).getPostTime()));
        try {
            PackageInfo info = pm.getPackageInfo(getItem(position).getPackageName(), GET_META_DATA);
            holder.ivImage.setImageDrawable(info.applicationInfo.loadIcon(pm));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.tvPostTime.setText(postTime);
        holder.tvTitle.setText(getItem(position).getTitle());
        if (!TextUtils.isEmpty(getItem(position).getText())) {
            holder.tvContent.setText(getItem(position).getText());
        } else if (!TextUtils.isEmpty(getItem(position).getTickerText())) {
            holder.tvContent.setText(getItem(position).getTickerText());
        } else {
            holder.tvContent.setText("");
        }
        holder.tvContentInfo.setText(getItem(position).getInfoText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.OnItemClick(holder.itemView, position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnRecyclerViewListener listener) {
        this.onClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
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
