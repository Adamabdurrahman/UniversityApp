package com.example.universityapp.Lecturer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private List<Grade> gradeList;

    public GradeAdapter(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grade grade = gradeList.get(position);

        holder.textName.setText(grade.getName());
        holder.textGrade.setText(grade.getGrade());
        holder.textClass.setText(grade.getClassName());

        // Set tulisan "Delete" di tombol
        holder.buttonDelete.setText("Delete");

        // Delete Action
        holder.buttonDelete.setOnClickListener(v -> {
            FirebaseFirestore.getInstance()
                    .collection("Grades")
                    .document(grade.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        gradeList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                            Log.e("GradeAdapter", "Error deleting document", e));
        });
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textGrade, textClass;
        Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textGrade = itemView.findViewById(R.id.textGrade);
            textClass = itemView.findViewById(R.id.textClass);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}