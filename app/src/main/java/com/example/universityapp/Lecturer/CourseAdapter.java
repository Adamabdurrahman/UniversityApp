package com.example.universityapp.Lecturer;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courseList;
    private OnCourseClickListener listener;

    // Interface untuk menangani klik Edit dan Delete
    public interface OnCourseClickListener {
        void onEditClick(Course course);
        void onDeleteClick(Course course);
    }

    public CourseAdapter(List<Course> courseList, OnCourseClickListener listener) {
        this.courseList = courseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courseList.get(position);

        // Menampilkan data ke dalam TextView
        holder.textCourseName.setText("Course: " + course.getCourseName());
        holder.textCourseCode.setText("Code: " + course.getCoursecode());
        holder.textLecturer.setText("Lecturer: " + course.getLecturerName());
        holder.textDescription.setText("Description: " + course.getCourseDescription());
        holder.textSchedule.setText("Schedule: " + course.getSchedule());
        holder.textMaterials.setText("Materials: " + course.getMaterials());

        // Menangani klik pada link Materials
        holder.textMaterials.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(course.getMaterials()));
            v.getContext().startActivity(intent);
        });

        // Menangani klik Edit
        holder.buttonEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(course);
        });

        // Menangani klik Delete
        holder.buttonDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(course);
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCourseName, textCourseCode, textLecturer, textDescription, textSchedule, textMaterials;
        Button buttonEdit, buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCourseName = itemView.findViewById(R.id.textCourseName);
            textCourseCode = itemView.findViewById(R.id.textCourseCode);
            textLecturer = itemView.findViewById(R.id.textLecturer);
            textDescription = itemView.findViewById(R.id.textDescription);
            textSchedule = itemView.findViewById(R.id.textSchedule);
            textMaterials = itemView.findViewById(R.id.textMaterials);
            buttonEdit = itemView.findViewById(R.id.buttonEditCourse);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteCourse);
        }
    }
}