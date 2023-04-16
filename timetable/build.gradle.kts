@file:Suppress("UnstableApiUsage")

version = "1.0.2"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitProperties)
}

dependencies {
    implementation(platform(libs.spring.cloud.bom))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.reactive)
    implementation(libs.kotlin.coroutines.reactor)
    implementation(libs.spring.boot.webflux)
    implementation(libs.spring.boot.actuator)
    implementation(libs.spring.cloud.config.client)
    implementation(libs.spring.cloud.eureka.client)
    implementation(libs.jackson.module.kotlin)

    kapt(libs.spring.boot.configProcessor)

    testImplementation(libs.spring.boot.test)
    testImplementation(libs.reactor.test)
    testImplementation(libs.junit5.core)
    testImplementation(libs.mockito.junit5)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlin.coroutines.test)

    testRuntimeOnly(libs.junit5.engine)
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
        jvmTarget = "17"
    }
}

tasks.processResources {
    filesMatching("application.yml") {
        expand(project.properties)
    }
}

tasks.test {
    useJUnitPlatform()
}
