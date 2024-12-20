package com.example.universityapp.Lecturer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.R;

public class AnnouncementsActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextBody, editTextRecipient;
    private Button buttonSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        // Inisialisasi View
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextBody = findViewById(R.id.editTextBody);
        editTextRecipient = findViewById(R.id.editTextRecipient);
        buttonSendEmail = findViewById(R.id.buttonSendEmail);

        // Tombol Kirim Email
        buttonSendEmail.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {
        String title = editTextTitle.getText().toString().trim();
        String body = editTextBody.getText().toString().trim();
        String recipient = editTextRecipient.getText().toString().trim();

        // Validasi input
        if (title.isEmpty() || body.isEmpty() || recipient.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intent untuk membuka aplikasi email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Hanya untuk aplikasi email
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(intent, "Choose Email Client"));
        } catch (Exception e) {
            Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show();
        }
    }
}