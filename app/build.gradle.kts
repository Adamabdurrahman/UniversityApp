plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.universityapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.universityapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.recyclerview)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ZXing untuk QR Code Scanner
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    // CameraX untuk pengambilan foto
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    implementation("com.google.firebase:firebase-ml-modeldownloader:24.2.2")
    implementation("org.tensorflow:tensorflow-lite:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.14.0") // jika menggunakan GPU

    implementation("com.google.mlkit:face-detection:16.1.5")

    implementation("com.google.guava:guava:31.1-android")


}