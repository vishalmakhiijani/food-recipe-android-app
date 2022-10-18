package com.meetvishalkumar.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.Models.Ingredient;
import com.meetvishalkumar.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionsingredientsAdapter extends RecyclerView.Adapter<InstructionsIngredientsViewHolder> {
    Context context;
    List<Ingredient> list;

    public InstructionsingredientsAdapter(Context context, List<Ingredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsIngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsIngredientsViewHolder holder, int position) {
        holder.TextView_Instructions_Step_Item.setText(list.get(position).name);
        holder.TextView_Instructions_Step_Item.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + list.get(position).image).into(holder.ImageView_instructions_Step_item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsIngredientsViewHolder extends RecyclerView.ViewHolder {
    ImageView ImageView_instructions_Step_item;
    TextView TextView_Instructions_Step_Item;

    public InstructionsIngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        ImageView_instructions_Step_item = itemView.findViewById(R.id.ImageView_instructions_Step_item);
        TextView_Instructions_Step_Item = itemView.findViewById(R.id.TextView_Instructions_Step_Item);
    }
}