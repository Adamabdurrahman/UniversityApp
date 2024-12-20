package com.example.universityapp;

import com.example.universityapp.Lecturer.LecturerDashboardActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    loginUser(email, password);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("LoginSuccess", "Successfully logged in as " + email);

                        // Navigasi berdasarkan email
                        if (email.equals("admin@gmail.com")) {
                            startActivity(new Intent(MainActivity.this, AdminDashboardActivity.class));
                        } else if (email.equals("lecturer@gmail.com"))
                            startActivity(new Intent(MainActivity.this, LecturerDashboardActivity.class)); // Lecturer Dashboard
                        else {
                            startActivity(new Intent(MainActivity.this, UserDashboardActivity.class));
                        }
                        Toast.makeText(MainActivity.this, "Welcome, " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle errors
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(MainActivity.this, "No such user exists", Toast.LENGTH_LONG).show();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Log.e("LoginFailure", "Failed to login", task.getException());
                    }
                });
    }
}