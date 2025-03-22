plugins {
    `java-library`
    kotlin("jvm")
}

group = "com.digitalsamurai.math"
version = "1.0.0"

java {

}
kotlin {
    jvmToolchain(21)
}
dependencies {
    testImplementation(libs.junit)
    implementation(kotlin("stdlib-jdk8"))
}
