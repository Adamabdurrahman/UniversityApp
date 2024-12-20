package com.example.universityapp.Lecturer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<String> scheduleList;

    public ScheduleAdapter(List<String> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewSchedule.setText(scheduleList.get(position));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSchedule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSchedule = itemView.findViewById(R.id.textViewSchedule);
        }
    }
}