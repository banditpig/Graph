import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}
group = "me.mikehoughton"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation(kotlin("test-junit5"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
tasks.test {
    useJUnitPlatform()
}