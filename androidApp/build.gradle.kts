plugins {
    id("com.android.application")
    kotlin("android")
}

val composeVersion = "1.1.1"
val pagerVersion = "0.19.0"

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "fr.insideapp.turnipoffkmm.android"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
        kotlinCompilerExtensionVersion = composeVersion
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("androidx.navigation:navigation-compose:2.4.2")
    implementation("io.coil-kt:coil-compose:2.0.0-rc01")

    implementation("com.google.accompanist:accompanist-pager:$pagerVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$pagerVersion")
}