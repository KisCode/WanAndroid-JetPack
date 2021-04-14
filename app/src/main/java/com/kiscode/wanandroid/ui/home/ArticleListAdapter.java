package com.kiscode.wanandroid.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kiscode.wanandroid.R;
import com.kiscode.wanandroid.model.ArticleModel;

import java.util.Objects;

/**
 * Description:
 * Author: keno
 * Date : 2021/4/13 8:10
 **/
public class ArticleListAdapter extends PagedListAdapter<ArticleModel, ArticleListAdapter.ArticleViewHolder> {
    protected ArticleListAdapter() {
        super(new DiffUtil.ItemCallback<ArticleModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull ArticleModel oldItem, @NonNull ArticleModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ArticleModel oldItem, @NonNull ArticleModel newItem) {
                return Objects.equals(oldItem.getTitle(), newItem.getTitle());
            }
        });
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_home, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleModel item = getItem(position);
        if (item == null) {
            return;
        }
        holder.tvTitle.setText(item.getTitle());
        holder.itemView.setOnClickListener(v -> {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(position);
            }
        });
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;


        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
} 