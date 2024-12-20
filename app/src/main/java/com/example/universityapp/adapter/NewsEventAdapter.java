package com.example.universityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;
import com.example.universityapp.model.NewsEventModel;
import com.example.universityapp.ui.EditNewsEventActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NewsEventAdapter extends RecyclerView.Adapter<NewsEventAdapter.NewsEventViewHolder> {

    private List<NewsEventModel> newsEventList;
    private Context context;

    public NewsEventAdapter(Context context, List<NewsEventModel> newsEventList) {
        this.context = context;
        this.newsEventList = newsEventList;
    }

    @NonNull
    @Override
    public NewsEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_news_event, parent, false);
        return new NewsEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsEventViewHolder holder, int position) {
        NewsEventModel newsEvent = newsEventList.get(position);
        holder.title.setText(newsEvent.getTitle());
        holder.description.setText("Description: " + newsEvent.getDescription());
        holder.date.setText("Date: " + newsEvent.getDate());

        // Edit button functionality
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditNewsEventActivity.class);
            intent.putExtra("newsEventId", newsEvent.getId());
            context.startActivity(intent);
        });

        // Delete button functionality
        holder.btnDelete.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("newsEvents").document(newsEvent.getId()).delete()
                    .addOnSuccessListener(aVoid -> {
                        newsEventList.remove(position);
                        notifyItemRemoved(position);
                    });
        });
    }

    @Override
    public int getItemCount() {
        return newsEventList.size();
    }

    static class NewsEventViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date;
        Button btnEdit, btnDelete;

        public NewsEventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            description = itemView.findViewById(R.id.textViewDescription);
            date = itemView.findViewById(R.id.textViewDate);
            btnEdit = itemView.findViewById(R.id.btnEditNewsEvent);
            btnDelete = itemView.findViewById(R.id.btnDeleteNewsEvent);
        }
    }
}