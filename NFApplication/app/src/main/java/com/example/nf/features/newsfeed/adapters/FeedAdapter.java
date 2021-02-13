package com.example.nf.features.newsfeed.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nf.R;
import com.example.nf.data.entity.Content;
import com.example.nf.data.entity.Field;
import com.example.nf.features.newsfeed.contract.FeedContract.OnFeedItemClickListener;
import com.example.nf.helpers.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Content> dataSource;
    private OnFeedItemClickListener listener;

    public FeedAdapter(@Nullable OnFeedItemClickListener listener) {
        dataSource = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_feed_item_vertical, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Content content = getItem(position);

        final int radius = (int) holder.itemView.getContext().getResources().getDimension(R.dimen.thumb_default_radius);
        final String url = (content.fields == null || content.fields.thumbnail == null) ? Field.PLACEHOLDER : content.fields.thumbnail;

        UIHelper.asyncLoadImage(holder.thumb, url, radius);
        holder.category.setText(content.sectionName);
        holder.title.setText(content.webTitle);
        holder.itemView.setOnClickListener(view -> listener.onFeedItemClicked(getItem(position)));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void addMoreDataSource(List<Content> dataSource) {
        int positionStart = this.dataSource.size();
        this.dataSource.addAll(dataSource);
        notifyItemRangeInserted(positionStart, dataSource.size());
    }

    public void addDataSource(List<Content> dataSource) {
        this.dataSource.addAll(0, dataSource);
        notifyItemRangeInserted(0, dataSource.size());
    }

    private Content getItem(int position) {
        return dataSource.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;
        AppCompatTextView category;
        AppCompatImageView thumb;

        ViewHolder(View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.feed_item_category);
            title = itemView.findViewById(R.id.feed_item_title);
            thumb = itemView.findViewById(R.id.feed_item_thumb);
        }
    }
}