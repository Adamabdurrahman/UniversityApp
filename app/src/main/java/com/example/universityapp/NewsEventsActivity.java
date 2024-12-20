package com.example.universityapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNewsEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_events);

        recyclerViewNewsEvents = findViewById(R.id.recyclerViewNewsEvents);
        recyclerViewNewsEvents.setLayoutManager(new LinearLayoutManager(this));

        // Hubungkan RecyclerView dengan adapter
        recyclerViewNewsEvents.setAdapter(new NewsEventsAdapter());
    }
}