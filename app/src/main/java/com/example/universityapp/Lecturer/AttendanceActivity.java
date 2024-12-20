package com.example.universityapp.Lecturer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universityapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class AttendanceActivity extends AppCompatActivity {

    private Button buttonGenerateQR, buttonScanQR;
    private ImageView imageViewQR;
    private FirebaseFirestore db;
    private String generatedQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        db = FirebaseFirestore.getInstance();

        buttonGenerateQR = findViewById(R.id.buttonGenerateQR);
        buttonScanQR = findViewById(R.id.buttonScanQR);
        imageViewQR = findViewById(R.id.imageViewQR);

        buttonGenerateQR.setOnClickListener(v -> generateAndSaveQRCode());
        buttonScanQR.setOnClickListener(v -> startQRScanner());
    }

    // Generate QR Code unik
    private void generateAndSaveQRCode() {
        generatedQRCode = "QR_" + System.currentTimeMillis();
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(generatedQRCode, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400);
            imageViewQR.setImageBitmap(bitmap);

            // Simpan ke Firestore
            Map<String, Object> qrData = new HashMap<>();
            qrData.put("qrCode", generatedQRCode);
            qrData.put("timestamp", System.currentTimeMillis());

            db.collection("QR_Codes").add(qrData)
                    .addOnSuccessListener(doc -> Toast.makeText(this, "QR Code Saved", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Log.e("Firestore", "Error saving QR", e));

        } catch (Exception e) {
            Log.e("QR", "Error generating QR", e);
        }
    }

    // Scanner QR Code
    private void startQRScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan the QR Code");
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureActivity.class);
        qrLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            validateQRCode(result.getContents());
        } else {
            Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_SHORT).show();
        }
    });

    // Validasi QR Code
    private void validateQRCode(String scannedQRCode) {
        db.collection("QR_Codes")
                .whereEqualTo("qrCode", scannedQRCode)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        showInputForm(scannedQRCode);
                    } else {
                        Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error validating QR", e));
    }

    private void showInputForm(String qrCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Attendance");

        View formView = getLayoutInflater().inflate(R.layout.form_input, null);
        builder.setView(formView);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            String name = ((android.widget.EditText) formView.findViewById(R.id.editTextName)).getText().toString();
            String className = ((android.widget.EditText) formView.findViewById(R.id.editTextClass)).getText().toString();

            saveAttendance(name, className, qrCode); // Menyimpan data attendance
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Menyimpan data ke Firestore
    private void saveAttendance(String name, String className, String qrCode) {
        Map<String, Object> attendance = new HashMap<>();
        attendance.put("name", name);
        attendance.put("class", className);
        attendance.put("qrCode", qrCode);
        attendance.put("timestamp", System.currentTimeMillis());

        db.collection("Attendance").add(attendance)
                .addOnSuccessListener(doc -> {
                    Toast.makeText(this, "Attendance Saved", Toast.LENGTH_SHORT).show();
                    openAttendanceList(); // Buka halaman list attendance
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving attendance", e));
    }

    // Membuka halaman list attendance
    private void openAttendanceList() {
        Intent intent = new Intent(this, AttendanceListActivity.class);
        startActivity(intent);
        finish();
    }
}