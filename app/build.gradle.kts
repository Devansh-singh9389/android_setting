plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.androidtvsetting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidtvsetting"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Strip unused languages to save space
        resourceConfigurations.addAll(listOf("en"))
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")

            // Enable R8 code shrinking and resource shrinking
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "src/main/keepRules/rules.keep"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}