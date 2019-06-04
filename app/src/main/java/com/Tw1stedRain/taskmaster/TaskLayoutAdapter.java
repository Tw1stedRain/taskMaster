package com.Tw1stedRain.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskLayoutAdapter extends RecyclerView.Adapter<TaskLayoutAdapter.TaskHolder> {


    public static class TaskHolder extends RecyclerView.ViewHolder {

        public TextView textName;
        public TextView textDescription;
        public TextView textState;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            this.textName = itemView.findViewById(R.id.task_name);
            this.textDescription = itemView.findViewById(R.id.task_description);
            this.textState = itemView.findViewById(R.id.task_state);
        }

        public void setTask(Task task) {
            this.textName.setText(task.getName());
            this.textDescription.setText(task.getDescription());
            this.textState.setText(String.format("%s", task.isAvailable()));
        }

    }


    private List<Task> tasks;

    public TaskLayoutAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.task_view, parent, false);

        TaskHolder holder = new TaskHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.setTask(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}
