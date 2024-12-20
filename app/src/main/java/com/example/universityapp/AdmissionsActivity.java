package com.example.universityapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdmissionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admissions);

        recyclerView = findViewById(R.id.recyclerViewAdmissions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hubungkan Adapter ke RecyclerView
        recyclerView.setAdapter(new AdmissionsAdapter());
    }
}