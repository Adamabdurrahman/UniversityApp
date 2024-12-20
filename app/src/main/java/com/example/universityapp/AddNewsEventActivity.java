package com.example.universityapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewsEventActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDate;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news_event);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        btnSave = findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(v -> addNewsEvent());
    }

    private void addNewsEvent() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();

        if (title.isEmpty() || description.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> newsEvent = new HashMap<>();
        newsEvent.put("title", title);
        newsEvent.put("description", description);
        newsEvent.put("date", date);

        db.collection("newsEvents").add(newsEvent)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "News Event added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add news event", Toast.LENGTH_SHORT).show());
    }
}