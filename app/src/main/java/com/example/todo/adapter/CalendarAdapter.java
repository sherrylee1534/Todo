package com.example.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.models.Task;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarAdapterViewHolder> {

    private List<Task> dailyGoals = new ArrayList<>();

    public static class CalendarAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView textCalendarTaskName;
        public TextView textCalendarTaskDescription;
        public TextView textCalendarTaskImportance;
        public TextView textCalendarModuleName;
        public TextView textCalendarDueTime;

        public CalendarAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textCalendarTaskName = itemView.findViewById(R.id.calendar_task_name);
            textCalendarTaskDescription = itemView.findViewById(R.id.calendar_task_description);
            textCalendarTaskImportance = itemView.findViewById(R.id.calendar_task_importance);
            textCalendarModuleName = itemView.findViewById(R.id.calendar_module_name);
            textCalendarDueTime = itemView.findViewById(R.id.calendar_due_time);
        }

        private void bind(Task task) {
            String taskName = task.taskName;
            String taskDescription = task.description;
            int taskImportance = task.importance;
            String taskModuleName = task.module;
            String taskDueTime = task.dueTime;

            // Bind data to non-null fields
            textCalendarTaskName.setText(taskName);
            textCalendarTaskImportance.setText(generateImportance(taskImportance));
            textCalendarModuleName.setText(taskModuleName);

            // Check for null before binding
            bindOrHideTextView(textCalendarTaskDescription, taskDescription);
            bindOrHideTextView(textCalendarDueTime, taskDueTime);

        }

        private String generateImportance(int importance) {
            String importanceString = "";

            for (int i = 0; i < importance; i++) {
                importanceString += "!";
            }

            return importanceString;
        }

        private void bindOrHideTextView(TextView textView, String data) {
            if (data == null) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(data);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    @NonNull
    @Override
    public CalendarAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendar_row_items, parent, false);
        return new CalendarAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapterViewHolder holder, int position) {
        holder.bind(dailyGoals.get(position));
    }

    @Override
    public int getItemCount() {
        return (dailyGoals == null) ? 0 : dailyGoals.size();
    }

    public void setDailyGoals(List<Task> dailyGoals) {
        this.dailyGoals = dailyGoals;
        notifyDataSetChanged();
    }

    public Task removeTask(int position) {
        Task completedTask = dailyGoals.get(position);
        dailyGoals.remove(completedTask);
        notifyItemRemoved(position);
        return completedTask;
    }

}
