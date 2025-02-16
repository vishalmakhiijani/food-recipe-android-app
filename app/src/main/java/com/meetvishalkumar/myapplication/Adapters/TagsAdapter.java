package com.meetvishalkumar.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.MainActivity;
import com.meetvishalkumar.myapplication.Models.Tag;
import com.meetvishalkumar.myapplication.R;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagViewHolder> {
    private List<Tag> tagList;
    private Context context;
    private OnTagClickListener listener;


    @NonNull
    @Override
    public TagsAdapter.TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tag, parent, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.TagViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.imageView.setImageResource(tag.getImageResId());
        holder.textView.setText(tag.getName());

        holder.itemView.setOnClickListener(v -> listener.onTagClick(tag.getName()));


    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    public interface OnTagClickListener {
        void onTagClick(String tagName);
    }
    public TagsAdapter(List<Tag> tagList, Context context, OnTagClickListener listener) {
        this.tagList = tagList;
        this.context = context;
        this.listener = listener;
    }
    public static class TagViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tag_image);
            textView = itemView.findViewById(R.id.tag_name);
        }
    }
}
