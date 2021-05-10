package com.example.todoapp;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Adapter.adapter2;

public class RecyclerItemTouchHelper1 extends ItemTouchHelper.SimpleCallback {

    private final adapter2 ad;
    public RecyclerItemTouchHelper1(adapter2 ad){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.ad = ad;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction == ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(ad.getContext());
            builder.setMessage(" Are you sure you want to this Delete Task? ");
            builder.setTitle("DELETE TASK");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> ad.deleteItem(viewHolder.getAdapterPosition()));
            builder.setNegativeButton("Cancel",
                    (dialog, which) -> ad.notifyItemChanged(viewHolder.getAdapterPosition()));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            ad.editItem(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable bckgrnd;
        View itemview = viewHolder.itemView;
        int offset = 20;
        if(dX>0){
            //LEFT
            icon = ContextCompat.getDrawable(ad.getContext(),R.drawable.updatebutton);
            bckgrnd = new ColorDrawable(ContextCompat.getColor(ad.getContext(),R.color.colorPrimary));
        }
        else {
            // Right
            icon = ContextCompat.getDrawable(ad.getContext(),R.drawable.ic_baseline_delete_24);
            bckgrnd = new ColorDrawable(Color.RED);
        }
        int margin = (itemview.getHeight() - icon.getIntrinsicHeight())/2;
        int top = itemview.getTop() + margin;
        int bottom = top + icon.getIntrinsicHeight();

        if(dX>0) {
            int left = itemview.getLeft() + margin;
            int right = itemview.getLeft() + margin +icon.getIntrinsicHeight();
            icon.setBounds(left,top,right,bottom);
            bckgrnd.setBounds(itemview.getLeft(),itemview.getTop(),
                    itemview.getLeft()+ ((int)dX)+offset,itemview.getBottom());
        } else if(dX<0) {
            int left = itemview.getRight() - margin -icon.getIntrinsicHeight();
            int right = itemview.getLeft() - margin ;
            icon.setBounds(left,top,right,bottom);
            bckgrnd.setBounds(itemview.getRight()+ ((int)dX) - offset,itemview.getTop(),
                    itemview.getRight(),itemview.getBottom());
        } else {
            bckgrnd.setBounds(0,0,0,0);
        }
        bckgrnd.draw(c);
        icon.draw(c);
    }
}
