package com.example.universityapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAlumniCareersActivity extends AppCompatActivity {

    private EditText etName, etCareer, etStory;
    private Button btnUpdate;
    private FirebaseFirestore db;
    private String alumniCareerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alumni_careers);

        etName = findViewById(R.id.etName);
        etCareer = findViewById(R.id.etCareer);
        etStory = findViewById(R.id.etStory);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();

        // Get the Alumni Career ID from the Intent
        alumniCareerId = getIntent().getStringExtra("alumniCareerId");

        if (TextUtils.isEmpty(alumniCareerId)) {
            Toast.makeText(this, "Invalid Alumni Career ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch existing data from Firestore
        populateData();

        btnUpdate.setOnClickListener(v -> updateAlumniCareer());
    }

    private void populateData() {
        db.collection("alumniandcareers").document(alumniCareerId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etName.setText(documentSnapshot.getString("name"));
                        etCareer.setText(documentSnapshot.getString("career"));
                        etStory.setText(documentSnapshot.getString("story"));
                    } else {
                        Toast.makeText(this, "Alumni Career not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show());
    }

    private void updateAlumniCareer() {
        String name = etName.getText().toString();
        String career = etCareer.getText().toString();
        String story = etStory.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(career) || TextUtils.isEmpty(story)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> alumniCareer = new HashMap<>();
        alumniCareer.put("name", name);
        alumniCareer.put("career", career);
        alumniCareer.put("story", story);

        db.collection("alumniandcareers").document(alumniCareerId).update(alumniCareer)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Alumni Career updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update Alumni Career", Toast.LENGTH_SHORT).show());
    }
}