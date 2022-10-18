package com.meetvishalkumar.myapplication.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.Models.Step;
import com.meetvishalkumar.myapplication.R;

import java.util.List;

public class InstructionsStepAdapter extends RecyclerView.Adapter<InstructionsStepViewHolder> {
    Context context;
    List<Step> list;

    public InstructionsStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsStepViewHolder holder, int position) {
        holder.textView_Instructions_step_Number.setText(String.valueOf(list.get(position).number));
        holder.textView_Instructions_step_Title.setText(list.get(position).step);

        holder.recycler_View_instructions_Ingredients.setHasFixedSize(true);
        holder.recycler_View_instructions_Ingredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsingredientsAdapter instructionsingredientsAdapter = new InstructionsingredientsAdapter(context, list.get(position).ingredients);
        holder.recycler_View_instructions_Ingredients.setAdapter(instructionsingredientsAdapter);

        holder.recycler_View_instructions_Equipment.setHasFixedSize(true);
        holder.recycler_View_instructions_Equipment.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionsEquipmentAdapter instructionsEquipmentAdapter = new InstructionsEquipmentAdapter(context, list.get(position).equipment);
        holder.recycler_View_instructions_Equipment.setAdapter(instructionsEquipmentAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsStepViewHolder extends RecyclerView.ViewHolder {
    TextView textView_Instructions_step_Number, textView_Instructions_step_Title;
    RecyclerView recycler_View_instructions_Equipment, recycler_View_instructions_Ingredients;

    public InstructionsStepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_Instructions_step_Number = itemView.findViewById(R.id.textView_Instructions_step_Number);
        textView_Instructions_step_Title = itemView.findViewById(R.id.textView_Instructions_step_Title);
        recycler_View_instructions_Equipment = itemView.findViewById(R.id.recycler_View_instructions_Equipment);
        recycler_View_instructions_Ingredients = itemView.findViewById(R.id.recycler_View_instructions_Ingredients);
    }
}
