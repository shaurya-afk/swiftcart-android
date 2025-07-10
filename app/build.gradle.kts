plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.hilt.android)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.swiftcartapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.swiftcartapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Hilt runtime
    implementation(libs.androidx.hilt.navigation.compose) // latest stable version
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.logging.interceptor)

    //NavController
    implementation(libs.androidx.navigation.compose)

    //Coil: image loading library
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-compose:2.2.0")
    implementation(libs.coil) // Coil for image loading
    implementation(libs.coil.compose.v210) // Coil for Jetpack Compose

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.4")

    implementation("androidx.compose.runtime:runtime:1.6.0")

    implementation( "androidx.compose.foundation:foundation:1.7.0") // Or at least 1.2.0
    implementation ("androidx.compose.material3:material3:1.3.0") // For Material3 components
    implementation( "androidx.compose.runtime:runtime:1.7.0" )// For collectAsState

    implementation ("androidx.compose.foundation:foundation:1.6.0")

    implementation( "androidx.core:core-ktx:1.13.1")
    implementation ("androidx.activity:activity-compose:1.9.3")
    implementation (platform("androidx.compose:compose-bom:2024.10.00"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.material3:material3")
    implementation ("io.coil-kt:coil-compose:2.7.0")

    implementation( "com.google.accompanist:accompanist-permissions:0.34.0")

    // room dB
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")


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
}