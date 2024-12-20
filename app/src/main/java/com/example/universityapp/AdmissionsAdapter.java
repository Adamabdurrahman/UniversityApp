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

public class AdmissionsAdapter extends RecyclerView.Adapter<AdmissionsAdapter.AdmissionViewHolder> {

    private List<Admission> admissionsList = new ArrayList<>();
    private FirebaseFirestore firestore;

    public AdmissionsAdapter() {
        // Inisialisasi Firestore
        firestore = FirebaseFirestore.getInstance();

        // Ambil data dari Firestore
        firestore.collection("admissions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        admissionsList.clear();
                        for (com.google.firebase.firestore.DocumentSnapshot document : task.getResult().getDocuments()) {
                            Admission admission = document.toObject(Admission.class);
                            admissionsList.add(admission);
                        }
                        notifyDataSetChanged();
                    } else {
                        // Handle error, jika ada
                    }
                });
    }

    @NonNull
    @Override
    public AdmissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admission, parent, false);
        return new AdmissionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionViewHolder holder, int position) {
        Admission admission = admissionsList.get(position);

        // Set data ke TextView
        holder.tvOrganizationName.setText(admission.getName());
        holder.tvRequirementDetail.setText("Requirement: " + admission.getRequirement());
        holder.tvProcessDetail.setText("Process: " + admission.getProcess());
        holder.tvDeadlineDetail.setText("Deadline: " + admission.getDeadline());
    }

    @Override
    public int getItemCount() {
        return admissionsList.size();
    }

    static class AdmissionViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrganizationName, tvRequirementDetail, tvProcessDetail, tvDeadlineDetail;

        public AdmissionViewHolder(View itemView) {
            super(itemView);
            tvOrganizationName = itemView.findViewById(R.id.tvOrganizationName);
            tvRequirementDetail = itemView.findViewById(R.id.tvRequirementDetail);
            tvProcessDetail = itemView.findViewById(R.id.tvProcessDetail);
            tvDeadlineDetail = itemView.findViewById(R.id.tvDeadlineDetail);
        }
    }
}