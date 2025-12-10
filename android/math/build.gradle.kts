plugins {
    `java-library`
    kotlin("jvm")
}
val projectVersion: String by rootProject.extra
group = "com.digitalsamurai.math"
version = projectVersion

java {

}
kotlin {
    jvmToolchain(21)
}
dependencies {
    testImplementation(libs.junit)
    implementation(kotlin("stdlib-jdk8"))
}
