package com.meetvishalkumar.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.Listeners.RecipeClickListener;
import com.meetvishalkumar.myapplication.Models.Recipe;
import com.meetvishalkumar.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecpieViewHolder> {
    Context context;
    List<Recipe> list;
    RecipeClickListener listener;


    public RandomRecipeAdapter(Context context, List<Recipe> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }

    @NonNull
    @Override
    public RandomRecpieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RandomRecpieViewHolder(LayoutInflater.from(context).inflate(R.layout.list_random_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecpieViewHolder holder, int position) {
        holder.TextView_Title.setText(list.get(position).title);
        holder.TextView_Title.setSelected(true);
//        holder.textView_Likes.setText(list.get(position).aggregateLikes + " Likes");
        holder.textView_Servings.setText(list.get(position).servings + " Servings");
        holder.textView_Time.setText(list.get(position).readyInMinutes + " Minutes");
        if (!list.get(position).diets.isEmpty()) {
            holder.textView_tags_frees.setText(list.get(position).diets + "");
        } else {
//            holder.textView_tags_frees.setText("");
        }
        holder.textView_tags_frees.setSelected(true);

        Picasso.get().load(list.get(position).image).into(holder.image_View_food);


        holder.Random_list_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecipeClick(String.valueOf(list.get(holder.getAdapterPosition()).id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class RandomRecpieViewHolder extends RecyclerView.ViewHolder {
    CardView Random_list_container;
    TextView TextView_Title, textView_Servings, textView_Time, textView_Likes, textView_tags_frees;
    ImageView image_View_food;

    public RandomRecpieViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_tags_frees = itemView.findViewById(R.id.textView_tags_frees);
        Random_list_container = itemView.findViewById(R.id.Random_list_container);
        TextView_Title = itemView.findViewById(R.id.TextView_Title);
        textView_Servings = itemView.findViewById(R.id.textView_Servings);
        textView_Time = itemView.findViewById(R.id.textView_Time);
        textView_Likes = itemView.findViewById(R.id.textView_Likes);
        image_View_food = itemView.findViewById(R.id.image_View_food);
    }
}