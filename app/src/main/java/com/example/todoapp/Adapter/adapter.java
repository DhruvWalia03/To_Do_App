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
import com.example.todoapp.Model.task_to_be_done;
import com.example.todoapp.MyDayActivity;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DataBaseHelper;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.TasksViewHolder> {
    private final List<task_to_be_done> tasklist;
    private final Context mCtx;
    String name;
    private final MyDayActivity activity;
    DataBaseHelper db;

    public adapter(DataBaseHelper db,MyDayActivity activity,List<task_to_be_done> tasklist, Context mCtx) {
        this.tasklist = tasklist;
        this.mCtx = mCtx;
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.task_to_be_done, null);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TasksViewHolder holder, int position) {
        final task_to_be_done i1 = tasklist.get(position);
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
        task_to_be_done item = tasklist.get(posn);
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(item.getId()));
        bundle.putString("name",item.getName());
        bundle.putString("time",item.getTime());
        Create_Task fragment = new Create_Task();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), MyDayActivity.TAG);
    }

    public void deleteItem(int posn)
    {
        task_to_be_done item = tasklist.get(posn);
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
            relativeLayout = itemView.findViewById(R.id.recyclerview);
            textViewtitle = itemView.findViewById(R.id.nameoftask);
            textViewdate = itemView.findViewById(R.id.date);
            textViewdesc = itemView.findViewById(R.id.description);
            textViewday = itemView.findViewById(R.id.day);
        }
    }
}