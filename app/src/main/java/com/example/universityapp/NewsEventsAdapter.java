package com.example.universityapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsEventsAdapter extends RecyclerView.Adapter<NewsEventsAdapter.NewsEventViewHolder> {

    private List<NewsEvent> newsEventList = new ArrayList<>();
    private FirebaseFirestore firestore;

    public NewsEventsAdapter() {
        // Inisialisasi Firestore
        firestore = FirebaseFirestore.getInstance();

        // Ambil data dari koleksi newsEvents
        firestore.collection("newsEvents")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        newsEventList.clear();
                        for (com.google.firebase.firestore.DocumentSnapshot document : task.getResult().getDocuments()) {
                            NewsEvent newsEvent = document.toObject(NewsEvent.class);
                            newsEventList.add(newsEvent);
                        }
                        notifyDataSetChanged();
                    } else {
                        // Handle errors jika ada
                    }
                });
    }

    @NonNull
    @Override
    public NewsEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_event, parent, false);
        return new NewsEventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsEventViewHolder holder, int position) {
        NewsEvent newsEvent = newsEventList.get(position);

        // Set data ke TextView
        holder.tvEventTitle.setText(newsEvent.getTitle());
        holder.tvEventDate.setText(newsEvent.getDate());
        holder.tvEventDescription.setText(newsEvent.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsEventList.size();
    }

    static class NewsEventViewHolder extends RecyclerView.ViewHolder {
        TextView tvEventTitle, tvEventDate, tvEventDescription;

        public NewsEventViewHolder(View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvEventDescription = itemView.findViewById(R.id.tvEventDescription);
        }
    }
}