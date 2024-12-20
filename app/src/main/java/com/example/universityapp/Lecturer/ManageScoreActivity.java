package com.example.universityapp.Lecturer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageScoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GradeAdapter gradeAdapter;
    private List<Grade> gradeList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_score);

        recyclerView = findViewById(R.id.recyclerViewGrades);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonAddData = findViewById(R.id.buttonAddData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gradeAdapter = new GradeAdapter(gradeList);
        recyclerView.setAdapter(gradeAdapter);

        db = FirebaseFirestore.getInstance();

        loadGradesFromFirestore();

        buttonAddData.setOnClickListener(v -> showAddDataDialog());
    }

    private void loadGradesFromFirestore() {
        db.collection("Grades").get()
                .addOnSuccessListener(querySnapshot -> {
                    gradeList.clear();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Grade grade = new Grade(
                                document.getId(),  // ID dokumen Firestore
                                document.getString("Name"),
                                document.getString("Grade"),
                                document.getString("Class")
                        );
                        gradeList.add(grade);
                    }
                    gradeAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching data", e));
    }

    private void showAddDataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Grade");

        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_grade, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) String name = ((android.widget.EditText) customLayout.findViewById(R.id.editTextName)).getText().toString();
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) String grade = ((android.widget.EditText) customLayout.findViewById(R.id.editTextGrade)).getText().toString();
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) String className = ((android.widget.EditText) customLayout.findViewById(R.id.editTextClass)).getText().toString();

            Map<String, Object> newGrade = new HashMap<>();
            newGrade.put("Name", name);
            newGrade.put("Grade", grade);
            newGrade.put("Class", className);

            db.collection("Grades").add(newGrade)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
                        loadGradesFromFirestore();
                    });
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}