package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.TasksViewHolder>
{
    private List<task_to_be_done> tasklist;
    private Context mCtx;

    public adapter(List<task_to_be_done> tasklist, Context mCtx) {
        this.tasklist = tasklist;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view =inflater.inflate(R.layout.task_to_be_done , null);
        TasksViewHolder holder=new TasksViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        task_to_be_done i1 =tasklist.get(position);
        holder.textViewtitle.setText(i1.getName());
        holder.textViewdesc.setText(i1.getDesc());
        holder.textViewdate.setText(i1.getDate());
        holder.textViewday.setText(i1.getDay());
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder{

        TextView textViewtitle, textViewdesc ,textViewdate ,textViewday;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewtitle = itemView.findViewById(R.id.nameoftask);
            textViewdate = itemView.findViewById(R.id.date);
            textViewdesc =itemView.findViewById(R.id.description);
            textViewday = itemView.findViewById(R.id.day);
        }
    }
    }

