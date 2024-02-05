plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.rolandoselvera.parkinglog"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rolandoselvera.parkinglog"
        minSdk = 22
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val coreKtxVersion = rootProject.extra["coreKtxVersion"]
    val appCompatVersion = rootProject.extra["appCompatVersion"]
    val materialVersion = rootProject.extra["materialVersion"]
    val constraintLayoutVersion = rootProject.extra["constraintLayoutVersion"]
    val navigationVersion = rootProject.extra["navigationVersion"]
    val lifecycleVersion = rootProject.extra["lifecycleVersion"]
    val roomVersion = rootProject.extra["roomVersion"]
    val easyValidationVersion = rootProject.extra["easyValidationVersion"]
    val coilVersion = rootProject.extra["coilVersion"]
    val preferencesVersion = rootProject.extra["preferencesVersion"]
    val gsonVersion = rootProject.extra["gsonVersion"]

    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Navigation libraries:
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // Lifecycle libraries:
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Preferences:
    implementation("androidx.preference:preference-ktx:$preferencesVersion")

    // Room:
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Validation Fields:
    implementation("com.wajahatkarim:easyvalidation-core:$easyValidationVersion")

    // Coil:
    implementation("io.coil-kt:coil:$coilVersion")

    // Gson:
    implementation("com.google.code.gson:gson:$gsonVersion")
}