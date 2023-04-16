@file:Suppress("UnstableApiUsage")

version = "1.0.0"

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
	implementation(libs.spring.cloud.eureka.server)
    implementation(libs.spring.boot.actuator)
}
