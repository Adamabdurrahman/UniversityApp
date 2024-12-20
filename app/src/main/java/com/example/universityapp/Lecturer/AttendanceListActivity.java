package com.example.universityapp.Lecturer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AttendanceListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView textViewAttendance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        db = FirebaseFirestore.getInstance();
        textViewAttendance = findViewById(R.id.textViewAttendance);

        loadAttendanceData();
    }

    private void loadAttendanceData() {
        db.collection("Attendance")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    StringBuilder data = new StringBuilder();

                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Attendance attendance = document.toObject(Attendance.class);

                        // Format timestamp ke tanggal
                        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                                .format(attendance.getTimestamp());

                        data.append("Name: ").append(attendance.getName()).append("\n")
                                .append("Class: ").append(attendance.getClassName()).append("\n")
                                .append("QR Code: ").append(attendance.getQrCode()).append("\n")
                                .append("Time: ").append(time).append("\n\n");
                    }

                    textViewAttendance.setText(data.toString());
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching attendance data", e));
    }
}