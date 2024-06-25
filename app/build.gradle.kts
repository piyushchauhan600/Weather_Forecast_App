plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // Hilt Plugins
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}



android {
    namespace = "com.pew.weatherforecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pew.weatherforecast"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.14"
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Dagger hilt dependencies
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    //Material icon dependencies
    implementation ("androidx.compose.material:material-icons-extended:1.6.8")
    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2")
    //Coil
    implementation("io.coil-kt:coil-compose:2.6.0")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    //Gson converter factory
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //Okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    //Room database
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    //Google Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7")
    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")
    //Material Api
    implementation("com.google.android.material:material:1.6.1")
    //Lottie Animation
    implementation("com.airbnb.android:lottie-compose:6.0.0")

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}