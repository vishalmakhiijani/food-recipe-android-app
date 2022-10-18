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
import com.meetvishalkumar.myapplication.Models.SimilarRecipeResponse;
import com.meetvishalkumar.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarRecipeAdapter extends RecyclerView.Adapter<SimilarRecipeViewHolder> {
    Context context;
    List<SimilarRecipeResponse> list;
    RecipeClickListener listener;

    public SimilarRecipeAdapter(Context context, List<SimilarRecipeResponse> list, RecipeClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarRecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.list_similar_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarRecipeViewHolder holder, int position) {
        holder.textView_similar_Title.setText(list.get(position).title);
        holder.textView_similar_Title.setSelected(true);
        holder.textView_similar_Serving.setText(list.get(position).servings + " Person");
        holder.textView_similar_Time.setText(list.get(position).readyInMinutes + " Minutes");
        Picasso.get().load("https://spoonacular.com/recipeImages/" + list.get(position).id + "-556x370." + list.get(position).imageType).into(holder.ImageView_similar);
        holder.similar_recipe_holder.setOnClickListener(new View.OnClickListener() {
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

class SimilarRecipeViewHolder extends RecyclerView.ViewHolder {
    CardView similar_recipe_holder;
    TextView textView_similar_Title, textView_similar_Serving, textView_similar_Time;
    ImageView ImageView_similar;

    public SimilarRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_similar_Time = itemView.findViewById(R.id.textView_similar_Time);
        similar_recipe_holder = itemView.findViewById(R.id.similar_recipe_holder);
        textView_similar_Title = itemView.findViewById(R.id.textView_similar_Title);
        textView_similar_Serving = itemView.findViewById(R.id.textView_similar_Serving);
        ImageView_similar = itemView.findViewById(R.id.ImageView_similar);
    }
}
