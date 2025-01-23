import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.compose.compiler)
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.chanu.photocache"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.chanu.photocache"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val wableDevBaseUrl = properties["base.url"] as? String ?: ""
        buildConfigField("String", "BASE_URL", wableDevBaseUrl)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeCompiler {
        includeSourceInformation = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // androidx
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // okhttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    // retrofit
    implementation(libs.bundles.retrofit)
    // timber
    implementation(libs.timber)
    // coroutine
    implementation(libs.bundles.coroutine)
    // hilt
    implementation(libs.bundles.hilt)
    ksp(libs.dagger.hilt.compiler)
    // kotlinx immutable
    implementation(libs.kotlinx.collections.immutable)
    // kotlinx serialization
    implementation(libs.kotlinx.serialization.json)
}
