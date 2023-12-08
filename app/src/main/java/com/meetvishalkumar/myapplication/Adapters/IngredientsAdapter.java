package com.meetvishalkumar.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.Models.ExtendedIngredient;
import com.meetvishalkumar.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
    Context context;
    List<ExtendedIngredient> list;

    public IngredientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_meal_ingredients, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.TextView_ingredients_name.setText(list.get(position).name);
        holder.TextView_ingredients_name.setSelected(true);
        holder.textview_ingredients_quantity.setText(list.get(position).original);
        holder.textview_ingredients_quantity.setSelected(true);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + list.get(position).image).into(holder.Image_view_ingredients);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class IngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView textview_ingredients_quantity, TextView_ingredients_name;
    ImageView Image_view_ingredients;

    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        textview_ingredients_quantity = itemView.findViewById(R.id.textview_ingredients_quantity);
        TextView_ingredients_name = itemView.findViewById((R.id.TextView_ingredients_name));
        Image_view_ingredients = itemView.findViewById(R.id.Image_view_ingredients);
    }
}
