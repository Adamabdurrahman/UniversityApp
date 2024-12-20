package com.example.universityapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlumniCareersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlumni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_careers);

        recyclerViewAlumni = findViewById(R.id.recyclerViewAlumni);
        recyclerViewAlumni.setLayoutManager(new LinearLayoutManager(this));

        // Hubungkan RecyclerView dengan adapter
        recyclerViewAlumni.setAdapter(new AlumniAdapter());
    }
}