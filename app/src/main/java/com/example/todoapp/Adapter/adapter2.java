package com.example.todoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Create_Task;
import com.example.todoapp.Create_imp;
import com.example.todoapp.Important;
import com.example.todoapp.Model.task_to_be_done1;
import com.example.todoapp.MyDayActivity;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DataBasehelper1;

import java.util.List;

public class adapter2 extends RecyclerView.Adapter<adapter2.TasksViewHolder> {
    private final List<task_to_be_done1> tasklist;
    private final Context mCtx;
    String name;
    private final Important activity;
    DataBasehelper1 db;

    public adapter2(DataBasehelper1 db, Important activity, List<task_to_be_done1> tasklist, Context mCtx) {
        this.tasklist = tasklist;
        this.mCtx = mCtx;
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.task_to_be_done1, null);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        final task_to_be_done1 i1 = tasklist.get(position);
        name=i1.getName();
        holder.textViewtitle.setText(name);
        holder.textViewdesc.setText(i1.getTime());
        holder.textViewdate.setText(i1.getDate());
        holder.textViewday.setText(i1.getDay());
        db.getWritableDatabase();
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public void editItem(int posn) {
        task_to_be_done1 item = tasklist.get(posn);
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(item.getId()));
        bundle.putString("name",item.getName());
        bundle.putString("time",item.getTime());
        Create_imp fragment = new Create_imp();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), Important.TAG);
    }

    public void deleteItem(int posn)
    {
        task_to_be_done1 item = tasklist.get(posn);
        activity.deleteData(item.getId(),posn);
    }

    public Context getContext() {
        return activity;
    }

    public static class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView textViewtitle;
        TextView textViewdesc;
        TextView textViewdate;
        TextView textViewday;
        RelativeLayout relativeLayout;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            textViewtitle = itemView.findViewById(R.id.nameoftask);
            textViewdate = itemView.findViewById(R.id.date);
            textViewdesc = itemView.findViewById(R.id.description);
            textViewday = itemView.findViewById(R.id.day);
        }
    }
}