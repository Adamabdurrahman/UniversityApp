package com.example.universityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityapp.R;
import com.example.universityapp.model.AlumniCareerModel;

import java.util.List;

public class AlumniCareerAdapter extends RecyclerView.Adapter<AlumniCareerAdapter.ViewHolder> {

    private Context context;
    private List<AlumniCareerModel> alumniCareers;

    public AlumniCareerAdapter(Context context, List<AlumniCareerModel> alumniCareers) {
        this.context = context;
        this.alumniCareers = alumniCareers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_alumni_career, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlumniCareerModel model = alumniCareers.get(position);
        holder.textViewName.setText(model.getName());
        holder.textViewCareer.setText(model.getCareer());
        holder.textViewStory.setText(model.getStory());
    }

    @Override
    public int getItemCount() {
        return alumniCareers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewCareer, textViewStory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCareer = itemView.findViewById(R.id.textViewCareer);
            textViewStory = itemView.findViewById(R.id.textViewStory);
        }
    }
}