@file:Suppress("UnstableApiUsage")

version = "1.1.0"

plugins {
    id("java")
    id("jacoco")
    alias(libs.plugins.spring.boot)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(platform(libs.spring.cloud.bom))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.webflux)
    implementation(libs.spring.cloud.config.client)
    implementation(libs.spring.cloud.gateway)
    implementation(libs.spring.cloud.eureka.client)
    implementation(libs.spring.boot.actuator)
    implementation(libs.spring.boot.security)
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test> {
    jvmArgs = listOf("--enable-preview")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("--enable-preview")
}