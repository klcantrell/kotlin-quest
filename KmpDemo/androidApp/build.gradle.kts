plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.kalalaucantrell.kmpdemo.android"
        minSdk = 27
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "${properties["version.compose"]}"
    }
}

dependencies {
    implementation(project(":Common"))
    implementation("com.google.android.material:material:${properties["version.material"]}")
    implementation("androidx.activity:activity-compose:${properties["version.activityCompose"]}")
    implementation("androidx.compose.material:material:${properties["version.compose"]}")
    implementation("androidx.compose.ui:ui-tooling:${properties["version.compose"]}")
}