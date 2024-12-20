package com.example.universityapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAlumniCareersActivity extends AppCompatActivity {

    private EditText etName, etCareer, etStory;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumni_careers);

        // Inisialisasi view
        etName = findViewById(R.id.etName);
        etCareer = findViewById(R.id.etCareer);
        etStory = findViewById(R.id.etStory);
        btnSave = findViewById(R.id.btnSave);

        // Inisialisasi Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Listener tombol save
        btnSave.setOnClickListener(v -> addAlumniCareer());
    }

    private void addAlumniCareer() {
        // Ambil data input dari user
        String name = etName.getText().toString().trim();
        String career = etCareer.getText().toString().trim();
        String story = etStory.getText().toString().trim();

        // Validasi input
        if (name.isEmpty() || career.isEmpty() || story.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simpan data ke Firestore
        Map<String, Object> alumniCareer = new HashMap<>();
        alumniCareer.put("name", name);
        alumniCareer.put("career", career);
        alumniCareer.put("story", story);

        db.collection("alumniandcareers")
                .add(alumniCareer)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Alumni Career added successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke activity sebelumnya
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add Alumni Career", Toast.LENGTH_SHORT).show());
    }
}