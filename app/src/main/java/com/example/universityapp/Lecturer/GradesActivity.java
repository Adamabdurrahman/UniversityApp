package com.example.universityapp.Lecturer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.universityapp.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GradesActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private FaceDetector faceDetector;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;

    private boolean faceDetected = false; // Flag agar hanya memproses sekali jika wajah valid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        Button buttonStartFaceDetection = findViewById(R.id.buttonStartFaceDetection);
        previewView = findViewById(R.id.previewView);

        // Konfigurasi Face Detector
        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
                .build();
        faceDetector = FaceDetection.getClient(options);

        cameraExecutor = Executors.newSingleThreadExecutor();

        buttonStartFaceDetection.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                startCamera();
            }
        });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Konfigurasi Preview
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // Pilih kamera depan
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build();

                // Konfigurasi Image Analysis
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, this::analyzeImage);

                cameraProvider.unbindAll();
                Camera camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageAnalysis);

            } catch (Exception e) {
                Log.e("GradesActivity", "Gagal memulai kamera", e);
                Toast.makeText(this, "Gagal memulai kamera", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void analyzeImage(ImageProxy image) {
        if (faceDetected) {
            image.close();
            return; // Jika wajah sudah valid, hentikan proses
        }

        try {
            if (image.getImage() == null) {
                image.close();
                return;
            }

            InputImage inputImage = InputImage.fromMediaImage(image.getImage(),
                    image.getImageInfo().getRotationDegrees());

            faceDetector.process(inputImage)
                    .addOnSuccessListener(faces -> {
                        for (Face face : faces) {
                            if (face.getBoundingBox() != null) { // Validasi bounding box
                                Log.d("FaceDetection", "Wajah terdeteksi di: " + face.getBoundingBox());
                                Toast.makeText(this, "Face Detected! You are Human.",
                                        Toast.LENGTH_SHORT).show();

                                faceDetected = true; // Tandai bahwa wajah valid
                                goToManageScore();
                                break; // Keluar dari loop setelah deteksi wajah valid
                            }
                        }
                        if (faces.isEmpty()) {
                            Log.d("FaceDetection", "Tidak ada wajah yang terdeteksi.");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FaceDetection", "Gagal mendeteksi wajah", e);
                    })
                    .addOnCompleteListener(task -> image.close());
        } catch (Exception e) {
            Log.e("FaceDetection", "Error pada analyzeImage", e);
            image.close();
        }
    }

    private void goToManageScore() {
        // Navigasi ke halaman ManageScore
        Intent intent = new Intent(GradesActivity.this, ManageScoreActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Izin kamera ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}