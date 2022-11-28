package com.meetvishalkumar.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.R;
import com.meetvishalkumar.myapplication.Models.Show_Data_Tips_Tricks;

import java.util.List;

public class Get_Tips_Tricks extends RecyclerView.Adapter<Get_Tips_Tricks.Tips_ViewHolder> {
    private final List<Show_Data_Tips_Tricks> list;
    private final Context context;

    public Get_Tips_Tricks(List<Show_Data_Tips_Tricks> list, Context context
    ) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Get_Tips_Tricks.Tips_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Tips_ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_recycler_view, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Tips_ViewHolder holder, int position) {
        Show_Data_Tips_Tricks show_data_tips_tricks = list.get(position);
        holder.Tips_And_Tricks_TextView_Title.setText(show_data_tips_tricks.getName() + ":");
        holder.Tips_And_Tricks_TextView_Desp.setText(show_data_tips_tricks.getContent());
//        holder.Tips_And_Tricks_TextView_Name.setText(Show_Data_Tips_Tricks.get);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Tips_ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Tips_And_Tricks_TextView_Title, Tips_And_Tricks_TextView_Desp,Tips_And_Tricks_TextView_Name;

        public Tips_ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tips_And_Tricks_TextView_Desp = itemView.findViewById(R.id.Tips_And_Tricks_TextView_Desp);
            Tips_And_Tricks_TextView_Title = itemView.findViewById(R.id.Tips_And_Tricks_TextView_Title);
            Tips_And_Tricks_TextView_Name = itemView.findViewById(R.id.Tips_And_Tricks_TextView_Name);
        }
    }
}
