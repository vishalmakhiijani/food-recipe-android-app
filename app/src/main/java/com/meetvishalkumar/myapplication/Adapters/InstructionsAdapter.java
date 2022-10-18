package com.meetvishalkumar.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meetvishalkumar.myapplication.Models.InstructionsResponse;
import com.meetvishalkumar.myapplication.R;

import java.util.List;

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsViewHolder> {
    Context context;
    List<InstructionsResponse> list;

    public InstructionsAdapter(Context context, List<InstructionsResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position) {
        holder.textView_Instructions_name.setText(list.get(position).name);
        holder.recyclear_instructions_steps.setHasFixedSize(true);
        holder.recyclear_instructions_steps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        InstructionsStepAdapter StepAdapter = new InstructionsStepAdapter(context, list.get(position).steps);
        holder.recyclear_instructions_steps.setAdapter(StepAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class InstructionsViewHolder extends RecyclerView.ViewHolder {
    TextView textView_Instructions_name;
    RecyclerView recyclear_instructions_steps;

    public InstructionsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_Instructions_name = itemView.findViewById(R.id.textView_Instructions_name);
        recyclear_instructions_steps = itemView.findViewById(R.id.recyclear_instructions_steps);
    }
}
