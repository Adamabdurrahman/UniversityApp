package com.example.universityapp.Lecturer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<Attendance> attendanceList;

    public AttendanceAdapter(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);

        holder.textViewName.setText("Name: " + attendance.getName());
        holder.textViewClass.setText("Class: " + attendance.getClassName());
        holder.textViewQRCode.setText("QR Code: " + attendance.getQrCode());

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(attendance.getTimestamp());
        holder.textViewTimestamp.setText("Time: " + formattedDate);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewClass, textViewQRCode, textViewTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewClass = itemView.findViewById(R.id.textViewClass);
            textViewQRCode = itemView.findViewById(R.id.textViewQRCode);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}