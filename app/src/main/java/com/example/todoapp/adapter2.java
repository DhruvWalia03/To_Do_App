package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class adapter2 extends RecyclerView.Adapter<adapter2.TasksViewHolder2> {
    private final List<task_to_be_done> tasklist;
    private final Context mCtx;
    private final OnNoteListener monNoteListener;
    String name;

    public adapter2(List<task_to_be_done> tasklist, Context mCtx, OnNoteListener onNoteListener) {
        this.tasklist = tasklist;
        this.mCtx = mCtx;
        this.monNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public TasksViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.task_to_be_done, null);
        return new TasksViewHolder2(view, monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder2 holder, int position) {
        final task_to_be_done i1 = tasklist.get(position);
        name=i1.getName();
        holder.textViewtitle.setText(name);
        holder.textViewdesc.setText(i1.getDesc());
        holder.textViewdate.setText(i1.getDate());
        holder.textViewday.setText(i1.getDay());
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public class TasksViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewtitle;
        TextView textViewdesc;
        TextView textViewdate;
        TextView textViewday;
        OnNoteListener onNoteListener;
        RelativeLayout relativeLayout;

        public TasksViewHolder2(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.recyclerview);
            textViewtitle = itemView.findViewById(R.id.nameoftask);
            textViewdate = itemView.findViewById(R.id.date);
            textViewdesc = itemView.findViewById(R.id.description);
            textViewday = itemView.findViewById(R.id.day);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition(), name);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position, String name);
    }
}