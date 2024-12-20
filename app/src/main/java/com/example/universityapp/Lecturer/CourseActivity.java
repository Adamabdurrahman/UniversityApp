package com.example.universityapp.Lecturer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.universityapp.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courseList;
    private FirebaseFirestore db;
    private Button buttonAddCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewCourses);
        buttonAddCourse = findViewById(R.id.buttonAddCourse);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseList = new ArrayList<>();

        adapter = new CourseAdapter(courseList, new CourseAdapter.OnCourseClickListener() {
            @Override
            public void onEditClick(Course course) {
                showFormDialog("Edit Course", course);
            }

            @Override
            public void onDeleteClick(Course course) {
                deleteCourse(course);
            }
        });
        recyclerView.setAdapter(adapter);

        buttonAddCourse.setOnClickListener(v -> showFormDialog("Add Course", null));
        loadCourses();
    }

    private void showFormDialog(String title, Course courseToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_course_form, null);
        builder.setView(view);

        EditText editTextName = view.findViewById(R.id.editTextCourseName);
        EditText editTextCode = view.findViewById(R.id.editTextCourseCode);
        EditText editTextLecturer = view.findViewById(R.id.editTextLecturer);
        EditText editTextSchedule = view.findViewById(R.id.editTextSchedule);
        EditText editTextMaterials = view.findViewById(R.id.editTextMaterials);

        if (courseToEdit != null) {
            editTextName.setText(courseToEdit.getCourseName());
            editTextCode.setText(courseToEdit.getCoursecode());
            editTextLecturer.setText(courseToEdit.getLecturerName());
            editTextSchedule.setText(courseToEdit.getSchedule());
            editTextMaterials.setText(courseToEdit.getMaterials());
        }

        builder.setPositiveButton("Save", (dialog, which) -> {
            String name = editTextName.getText().toString();
            String code = editTextCode.getText().toString();
            String lecturer = editTextLecturer.getText().toString();
            String schedule = editTextSchedule.getText().toString();
            String materials = editTextMaterials.getText().toString();

            if (courseToEdit == null) addNewCourse(name, code, lecturer, schedule, materials);
            else editCourse(courseToEdit, name, code, lecturer, schedule, materials);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void addNewCourse(String name, String code, String lecturer, String schedule, String materials) {
        Map<String, Object> course = new HashMap<>();
        course.put("courseName", name);
        course.put("coursecode", code);
        course.put("lecturerName", lecturer);
        course.put("schedule", schedule);
        course.put("materials", materials);

        db.collection("Courses").add(course).addOnSuccessListener(doc -> loadCourses());
    }

    private void editCourse(Course course, String name, String code, String lecturer, String schedule, String materials) {
        db.collection("Courses").document(course.getDocumentId())
                .update("courseName", name, "coursecode", code, "lecturerName", lecturer,
                        "schedule", schedule, "materials", materials)
                .addOnSuccessListener(doc -> loadCourses());
    }

    private void deleteCourse(Course course) {
        db.collection("Courses").document(course.getDocumentId()).delete()
                .addOnSuccessListener(doc -> loadCourses());
    }

    private void loadCourses() {
        db.collection("Courses").get().addOnSuccessListener(query -> {
            courseList.clear();
            for (QueryDocumentSnapshot doc : query) {
                Course course = doc.toObject(Course.class);
                course.setDocumentId(doc.getId());
                courseList.add(course);
            }
            adapter.notifyDataSetChanged();
        });
    }
}