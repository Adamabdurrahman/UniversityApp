package com.example.universityapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.AddAlumniCareersActivity;
import com.example.universityapp.R;
import com.example.universityapp.adapter.AlumniCareerAdapter;
import com.example.universityapp.model.AlumniCareerModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminAlumniCareersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlumniCareerAdapter adapter;
    private List<AlumniCareerModel> alumniCareers;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_alumni_careers);

        recyclerView = findViewById(R.id.recyclerViewAlumniCareers);
        Button btnAddAlumniCareer = findViewById(R.id.btnAddAlumniCareer);

        db = FirebaseFirestore.getInstance();
        alumniCareers = new ArrayList<>();
        adapter = new AlumniCareerAdapter(this, alumniCareers);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAddAlumniCareer.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAlumniCareersActivity.class);
            startActivity(intent);
        });

        loadAlumniCareers();
    }

    private void loadAlumniCareers() {
        db.collection("alumniandcareers").get()
                .addOnSuccessListener(querySnapshot -> {
                    alumniCareers.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot document : querySnapshot.getDocuments()) {
                        AlumniCareerModel model = document.toObject(AlumniCareerModel.class);
                        if (model != null) {
                            model.setId(document.getId());
                            alumniCareers.add(model);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAlumniCareers();
    }
}