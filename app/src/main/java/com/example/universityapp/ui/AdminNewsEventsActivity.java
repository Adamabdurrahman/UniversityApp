package com.example.universityapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.AddNewsEventActivity;
import com.example.universityapp.R;
import com.example.universityapp.adapter.NewsEventAdapter;
import com.example.universityapp.model.NewsEventModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AdminNewsEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsEventAdapter adapter;
    private ArrayList<NewsEventModel> newsEventList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news_events);

        recyclerView = findViewById(R.id.recyclerViewNewsEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsEventList = new ArrayList<>();
        adapter = new NewsEventAdapter(this, newsEventList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        findViewById(R.id.btnAddNewsEvent).setOnClickListener(v -> {
            startActivity(new Intent(AdminNewsEventsActivity.this, AddNewsEventActivity.class));
        });

        fetchNewsEvents();
    }

    private void fetchNewsEvents() {
        db.collection("newsEvents").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                newsEventList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    NewsEventModel newsEvent = document.toObject(NewsEventModel.class);
                    newsEvent.setId(document.getId());
                    newsEventList.add(newsEvent);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}