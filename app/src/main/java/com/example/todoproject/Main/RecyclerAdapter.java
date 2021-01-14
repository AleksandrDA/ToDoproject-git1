package com.example.todoproject.Main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoproject.DataBase.Room.Tasks;
import com.example.todoproject.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private static final String TAG = "myLogs";

    private List<Tasks> allTasks;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView itemRecyclerTask;
        CheckBox checkBoxTask;
        ImageView imgStopWatchMainAct;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemRecyclerTask = itemView.findViewById(R.id.item_recycler_task);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            imgStopWatchMainAct = itemView.findViewById(R.id.imgStopWatchMainAct);

        }
    }

    public RecyclerAdapter(List<Tasks> tasks) {
            allTasks = tasks;
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(itemView);

        Log.d(TAG,"onCreateViewHolder in class RecyclerAdapter");
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        //viewHolder.itemRecyclerTask.setText(allTasks);
        Tasks task = allTasks.get(position);
        viewHolder.itemRecyclerTask.setText(task.getTaskName());

        //просто пробный вариант
        int currentPosition = 2;
        if(currentPosition == position) {
            viewHolder.imgStopWatchMainAct.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if(allTasks != null){
            return allTasks.size();
        } else {
            return 0;
        }
    }
}
