package com.example.universityapp.Lecturer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.Lecturer.AnnouncementsActivity;
import com.example.universityapp.Lecturer.AttendanceActivity;
import com.example.universityapp.Lecturer.CourseActivity;
import com.example.universityapp.Lecturer.GradesActivity;
import com.example.universityapp.Lecturer.ScheduleActivity;
import com.example.universityapp.R;

public class LecturerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_dashboard);

        // Klik Manage Course Materials
        findViewById(R.id.cardManageMaterials).setOnClickListener(v -> {
            startActivity(new Intent(this, CourseActivity.class));
        });

        // Klik Manage Grades
        findViewById(R.id.cardManageGrades).setOnClickListener(v -> {
            startActivity(new Intent(this, GradesActivity.class));
        });

        // Klik Announcements
        findViewById(R.id.cardAnnouncements).setOnClickListener(v -> {
            startActivity(new Intent(this, AnnouncementsActivity.class));
        });

        // Klik Class Schedule
        findViewById(R.id.cardSchedule).setOnClickListener(v -> {
            startActivity(new Intent(this, ScheduleActivity.class));
        });

        // Klik Attendance
        findViewById(R.id.cardAttendance).setOnClickListener(v -> {
            startActivity(new Intent(this, AttendanceActivity.class));
        });
    }
}