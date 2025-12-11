import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}
// TODO: readme с нормальным человеческим описанием того, как работает
val projectVersion: String by rootProject.extra

// emulator -- for implement testing in docker container with emulator
// local -- for implement testing in docker container using localhost
// global -- for global production telemetry
val otelHost = when (val otelHostType: String = gradleLocalProperties(rootDir, providers).getProperty("otel_host_type")) {
    "emulator" -> "http://10.0.2.2:4318"
    "global" -> "http://0.0.0.0:4318"
    "local" -> "http://10.97.96.117:4318" // TODO: вот тут надо как-то запровайдить не просто локальный хост, а айпишник хоста в локальной сети (на серваке биндится 0.0.0.0)
    else -> throw GradleException("Current: <${otelHostType}>. Please, provide otel_host_type in local.properties with one value of: local, emulator, global")
}
android {
    namespace = "com.digitsamurai.core.otel"
    compileSdk = 34

    with(buildFeatures) {
        this.buildConfig = true
    }

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "PROJECT_VERSION", "\"$projectVersion\"")
        buildConfigField("String", "OTEL_HOST", "\"$otelHost\"")
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
    api(libs.otel.kotlin.extension)
    api(libs.otel.api.incubator)
    api(libs.otel.exporter.otlp)
    api(libs.otel.api)
    api(libs.otel.android.agent)
    implementation(libs.androidx.core.ktx)
}