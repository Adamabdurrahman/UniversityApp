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

public class EditNewsEventActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etDate;
    private Button btnUpdate;
    private FirebaseFirestore db;
    private String newsEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news_event);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();

        // Get the News Event ID from the Intent
        newsEventId = getIntent().getStringExtra("newsEventId");

        if (TextUtils.isEmpty(newsEventId)) {
            Toast.makeText(this, "Invalid News Event ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch existing data from Firestore
        populateData();

        btnUpdate.setOnClickListener(v -> updateNewsEvent());
    }

    private void populateData() {
        db.collection("newsEvents").document(newsEventId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etTitle.setText(documentSnapshot.getString("title"));
                        etDescription.setText(documentSnapshot.getString("description"));
                        etDate.setText(documentSnapshot.getString("date"));
                    } else {
                        Toast.makeText(this, "News Event not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show());
    }

    private void updateNewsEvent() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> newsEvent = new HashMap<>();
        newsEvent.put("title", title);
        newsEvent.put("description", description);
        newsEvent.put("date", date);

        db.collection("newsEvents").document(newsEventId).update(newsEvent)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "News Event updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update News Event", Toast.LENGTH_SHORT).show());
    }
}