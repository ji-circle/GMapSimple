import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}


android {
    namespace = "com.example.gmapsimple"
    compileSdk = 35

    val properties = Properties()
    properties.load(FileInputStream(rootProject.file("local.properties")))

    defaultConfig {
        applicationId = "com.example.gmapsimple"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resValue("string","google_maps_key","AIzaSyBjB8ZQ4-Dds48-gF6GvxPYYmoo0hyJF5U")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    // Jetpack Compose
//    implementation(libs.ui)
//    implementation(libs.androidx.material)
//    implementation(libs.ui.tooling.preview)
//    implementation(libs.androidx.lifecycle.runtime.compose)

//
////    implementation("com.google.android.gms:play-services-maps:18.1.0")
////    implementation("com.google.android.gms:play-services-location:21.3.0")
////    implementation("com.google.maps.android:maps-compose:2.9.0")
//
//    implementation(libs.play.services.maps.v1810)
//    implementation(libs.play.services.location)
//    implementation(libs.maps.compose)

    // ViewModel
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Google Maps
    implementation(libs.play.services.maps)

    // Coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


    //
//    // Jetpack Compose
//    implementation("androidx.compose.ui:ui:1.7.5")
//    implementation("androidx.compose.material:material:1.7.5")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.7.5")
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
//
//    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
//
//    // Retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.11.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
//
//    // Google Maps
//    implementation("com.google.android.gms:play-services-maps:19.0.0")
//
//    // Coroutines
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

//    implementation("com.google.maps.android:android-maps-utils:3.8.0")
    implementation(libs.android.maps.utils)
//    implementation("com.google.maps.android:maps-utils-ktx:5.1.1")
    implementation(libs.maps.utils.ktx)

//    implementation("com.google.maps.android:maps-compose:2.15.0")
//    implementation("com.google.android.gms:play-services-maps:19.0.0")
//
    implementation(libs.maps.compose.v2150)
    implementation(libs.play.services.maps)
}