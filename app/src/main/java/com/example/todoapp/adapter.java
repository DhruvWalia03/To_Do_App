package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.TasksViewHolder> {
    private List<task_to_be_done> tasklist;
    private Context mCtx;
    private OnNoteListener monNoteListener;

    public adapter(List<task_to_be_done> tasklist, Context mCtx, OnNoteListener onNoteListener) {
        this.tasklist = tasklist;
        this.mCtx = mCtx;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.task_to_be_done, null);
        TasksViewHolder holder = new TasksViewHolder(view, monNoteListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TasksViewHolder holder, int position) {
        final task_to_be_done i1 = tasklist.get(position);
        holder.textViewtitle.setText(i1.getName());
        holder.textViewdesc.setText(i1.getDesc());
        holder.textViewdate.setText(i1.getDate());
        holder.textViewday.setText(i1.getDay());
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewtitle, textViewdesc, textViewdate, textViewday, textViewId;
        OnNoteListener onNoteListener;
        RelativeLayout relativeLayout;

        public TasksViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            relativeLayout= itemView.findViewById(R.id.recyclerview);
            textViewtitle = itemView.findViewById(R.id.nameoftask);
            textViewdate = itemView.findViewById(R.id.date);
            textViewdesc = itemView.findViewById(R.id.description);
            textViewday = itemView.findViewById(R.id.day);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition(), String.valueOf(textViewtitle));
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position,String id);
    }
}

