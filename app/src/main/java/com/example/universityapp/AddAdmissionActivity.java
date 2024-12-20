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

public class AddAdmissionActivity extends AppCompatActivity {

    private EditText etName, etDeadline, etProcess, etRequirement;
    private Button btnSave;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admission);

        etName = findViewById(R.id.etName);
        etDeadline = findViewById(R.id.etDeadline);
        etProcess = findViewById(R.id.etProcess);
        etRequirement = findViewById(R.id.etRequirement);
        btnSave = findViewById(R.id.btnSave);

        db = FirebaseFirestore.getInstance();

        btnSave.setOnClickListener(v -> addAdmission());
    }

    private void addAdmission() {
        String name = etName.getText().toString();
        String deadline = etDeadline.getText().toString();
        String process = etProcess.getText().toString();
        String requirement = etRequirement.getText().toString();

        if (name.isEmpty() || deadline.isEmpty() || process.isEmpty() || requirement.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> admission = new HashMap<>();
        admission.put("name", name);
        admission.put("deadline", deadline);
        admission.put("process", process);
        admission.put("requirement", requirement);

        db.collection("admissions").add(admission)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Admission added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add admission", Toast.LENGTH_SHORT).show());
    }
}