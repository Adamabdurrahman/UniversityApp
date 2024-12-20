package com.example.universityapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditAdmissionActivity extends AppCompatActivity {

    private EditText etName, etDeadline, etProcess, etRequirement;
    private Button btnUpdate;
    private FirebaseFirestore db;
    private String admissionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admission);

        etName = findViewById(R.id.etName);
        etDeadline = findViewById(R.id.etDeadline);
        etProcess = findViewById(R.id.etProcess);
        etRequirement = findViewById(R.id.etRequirement);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();

        // Get admissionId from Intent
        admissionId = getIntent().getStringExtra("admissionId");

        if (TextUtils.isEmpty(admissionId)) {
            Toast.makeText(this, "Invalid Admission ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Populate data if needed (optional, if you fetch details first)
        populateData();

        btnUpdate.setOnClickListener(v -> updateAdmission());
    }

    private void populateData() {
        // Fetch data from Firestore to populate fields (optional, if required)
        db.collection("admissions").document(admissionId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etName.setText(documentSnapshot.getString("name"));
                        etDeadline.setText(documentSnapshot.getString("deadline"));
                        etProcess.setText(documentSnapshot.getString("process"));
                        etRequirement.setText(documentSnapshot.getString("requirement"));
                    } else {
                        Toast.makeText(this, "Admission not found", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show());
    }

    private void updateAdmission() {
        String name = etName.getText().toString();
        String deadline = etDeadline.getText().toString();
        String process = etProcess.getText().toString();
        String requirement = etRequirement.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(deadline) || TextUtils.isEmpty(process) || TextUtils.isEmpty(requirement)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> admission = new HashMap<>();
        admission.put("name", name);
        admission.put("deadline", deadline);
        admission.put("process", process);
        admission.put("requirement", requirement);

        db.collection("admissions").document(admissionId).update(admission)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Admission updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update admission", Toast.LENGTH_SHORT).show());
    }
}