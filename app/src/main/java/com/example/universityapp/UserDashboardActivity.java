package com.example.universityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserDashboardActivity extends AppCompatActivity {

    private Button btnAdmissions, btnNewsEvents, btnAlumniCareers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        btnAdmissions = findViewById(R.id.btnAdmissions);
        btnNewsEvents = findViewById(R.id.btnNewsEvents);
        btnAlumniCareers = findViewById(R.id.btnAlumniCareers);

        btnAdmissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, AdmissionsActivity.class));
            }
        });

        btnNewsEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, NewsEventsActivity.class));
            }
        });

        btnAlumniCareers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, AlumniCareersActivity.class));
            }
        });
    }
}