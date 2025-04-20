plugins {
    `java-library`
    kotlin("jvm")
}

group = "com.digitalsamurai.otel.api"
version = "1.0.0"
java {

}

kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(libs.otel.api)
}