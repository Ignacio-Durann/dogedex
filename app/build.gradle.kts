plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.navigation.safe)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.app.dogedex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.dogedex"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.androidx.leanback)
    implementation(libs.retrofit)
    implementation(libs.retrofitJson)
    implementation(libs.moshiRetrofit)
    implementation(libs.interceptorLoggin)
    implementation(libs.coil.compose)
    implementation(libs.cameraX.core)
    implementation(libs.cameraX.camera2)
    implementation(libs.cameraX.lifecicle)
    implementation(libs.cameraX.view)
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.sopport)
//    jetpack compose
    implementation(libs.material3) // o
    implementation(libs.material2) // o
    implementation(libs.foundation) // o
    implementation(libs.compose.ui)

    // Soporte para Preview en Android Studio
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)

    // Tests de UI
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

    // Íconos opcionales
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)

    // Adaptive layout opcional
    implementation(libs.material3.adaptive)

    // Integraciones opcionales
    implementation(libs.activity.compose)
    implementation(libs.viewmodel.compose)
    implementation(libs.runtime.livedata)
    implementation(libs.runtime.rxjava2)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}