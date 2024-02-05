// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("coreKtxVersion", "1.12.0")
        set("appCompatVersion", "1.6.1")
        set("materialVersion", "1.11.0")
        set("constraintLayoutVersion", "2.1.4")
        set("navigationVersion", "2.7.6")
        set("lifecycleVersion", "2.7.0")
        set("roomVersion", "2.6.1")
        set("easyValidationVersion", "1.0.4")
        set("coilVersion", "2.5.0")
        set("preferencesVersion", "1.2.1")
        set("gsonVersion", "2.10.1")
    }

    dependencies {
        val navVersion = rootProject.extra["navigationVersion"]
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}