package com.example.universityapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.AddAdmissionActivity;
import com.example.universityapp.R;
import com.example.universityapp.adapter.AdmissionAdapter;
import com.example.universityapp.model.AdmissionModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminAdmissionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdmissionAdapter adapter;
    private ArrayList<AdmissionModel> admissionList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_admissions);

        recyclerView = findViewById(R.id.recyclerViewAdmissions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        admissionList = new ArrayList<>();
        adapter = new AdmissionAdapter(this, admissionList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        findViewById(R.id.btnAddAdmission).setOnClickListener(v -> {
            startActivity(new Intent(AdminAdmissionsActivity.this, AddAdmissionActivity.class));
        });

        fetchAdmissions();
    }

    private void fetchAdmissions() {
        db.collection("admissions").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                admissionList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    AdmissionModel admission = document.toObject(AdmissionModel.class);
                    admission.setId(document.getId());
                    admissionList.add(admission);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}