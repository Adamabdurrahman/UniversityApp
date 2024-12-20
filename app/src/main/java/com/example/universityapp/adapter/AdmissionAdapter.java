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
import com.example.universityapp.model.AdmissionModel;
import com.example.universityapp.ui.EditAdmissionActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdmissionAdapter extends RecyclerView.Adapter<AdmissionAdapter.AdmissionViewHolder> {

    private List<AdmissionModel> admissionList;
    private Context context;

    public AdmissionAdapter(Context context, List<AdmissionModel> admissionList) {
        this.context = context;
        this.admissionList = admissionList;
    }

    @NonNull
    @Override
    public AdmissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_admission, parent, false);
        return new AdmissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmissionViewHolder holder, int position) {
        AdmissionModel admission = admissionList.get(position);
        holder.name.setText(admission.getName());
        holder.deadline.setText("Deadline: " + admission.getDeadline());
        holder.process.setText("Process: " + admission.getProcess());
        holder.requirement.setText("Requirement: " + admission.getRequirement());

        // Fungsi Tombol Edit
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditAdmissionActivity.class);
            intent.putExtra("admissionId", admission.getId());
            context.startActivity(intent);
        });

        // Fungsi Tombol Delete
        holder.btnDelete.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("admissions").document(admission.getId()).delete()
                    .addOnSuccessListener(aVoid -> {
                        admissionList.remove(position);
                        notifyItemRemoved(position);
                    });
        });
    }

    @Override
    public int getItemCount() {
        return admissionList.size();
    }

    static class AdmissionViewHolder extends RecyclerView.ViewHolder {
        TextView name, deadline, process, requirement;
        Button btnEdit, btnDelete;

        public AdmissionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            deadline = itemView.findViewById(R.id.textViewDeadline);
            process = itemView.findViewById(R.id.textViewProcess);
            requirement = itemView.findViewById(R.id.textViewRequirement);
            btnEdit = itemView.findViewById(R.id.btnEditAdmission);
            btnDelete = itemView.findViewById(R.id.btnDeleteAdmission);
        }
    }
}