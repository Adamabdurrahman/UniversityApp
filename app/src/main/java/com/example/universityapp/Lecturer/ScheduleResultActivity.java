package com.example.universityapp.Lecturer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ScheduleResultActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView textViewResults;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_result);

        textViewResults = findViewById(R.id.textViewResults);
        db = FirebaseFirestore.getInstance();

        loadScheduleResults();
    }

    // Method untuk mengubah angka hari menjadi nama hari
    private String getDayName(int dayNumber) {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return dayNumber >= 0 && dayNumber < 7 ? days[dayNumber] : "Invalid Day";
    }

    // Method untuk mengambil hasil jadwal dari Firestore dan menampilkannya
    private void loadScheduleResults() {
        db.collection("AutoGeneratedSchedules")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    StringBuilder results = new StringBuilder();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Ambil data dari Firestore
                        String studentCount = document.get("studentCount") != null ? document.get("studentCount").toString() : "N/A";
                        int dayNumber = document.get("day") != null ? Integer.parseInt(document.get("day").toString()) : -1;
                        String predictedTime = document.get("predictedTime") != null ? document.get("predictedTime").toString() : "N/A";

                        // Konversi dayNumber ke nama hari
                        String dayName = getDayName(dayNumber);

                        // Tambahkan data ke hasil tampilan
                        results.append("Total Students: ").append(studentCount)
                                .append("\nDay: ").append(dayName)
                                .append("\nTime: ").append(predictedTime)
                                .append("\n\n");
                    }
                    textViewResults.setText(results.toString());
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error loading results", e));
    }
}