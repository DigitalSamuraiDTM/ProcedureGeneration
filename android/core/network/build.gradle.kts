
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.digitsamurai.opentelemetry.example.core.network"
    compileSdk = 34

    with(buildFeatures) {
        this.buildConfig = true
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}
dependencies {
    implementation(project(":android:core:otel"))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.contentnegotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.okhttp)
//    api(projects.general.transfer)
}
