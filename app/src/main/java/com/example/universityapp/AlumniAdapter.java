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

public class AlumniAdapter extends RecyclerView.Adapter<AlumniAdapter.AlumniViewHolder> {

    private List<Alumni> alumniList = new ArrayList<>();
    private FirebaseFirestore firestore;

    public AlumniAdapter() {
        // Inisialisasi Firestore
        firestore = FirebaseFirestore.getInstance();

        // Ambil data dari koleksi alumni_and_careers
        firestore.collection("alumniandcareers")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        alumniList.clear();
                        for (com.google.firebase.firestore.DocumentSnapshot document : task.getResult().getDocuments()) {
                            Alumni alumni = document.toObject(Alumni.class);
                            alumniList.add(alumni);
                        }
                        notifyDataSetChanged();
                    } else {
                        // Handle errors jika ada
                    }
                });
    }

    @NonNull
    @Override
    public AlumniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumni, parent, false);
        return new AlumniViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumniViewHolder holder, int position) {
        Alumni alumni = alumniList.get(position);

        // Set data ke TextView
        holder.tvAlumniName.setText(alumni.getName());
        holder.tvAlumniCareer.setText(alumni.getCareer());
        holder.tvAlumniStory.setText(alumni.getStory());
    }

    @Override
    public int getItemCount() {
        return alumniList.size();
    }

    static class AlumniViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlumniName, tvAlumniCareer, tvAlumniStory;

        public AlumniViewHolder(View itemView) {
            super(itemView);
            tvAlumniName = itemView.findViewById(R.id.tvAlumniName);
            tvAlumniCareer = itemView.findViewById(R.id.tvAlumniCareer);
            tvAlumniStory = itemView.findViewById(R.id.tvAlumniStory);
        }
    }
}