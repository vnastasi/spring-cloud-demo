@file:Suppress("UnstableApiUsage")

version = "1.0.2"

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.dokka")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("com.squareup.okhttp3:mockwebserver")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin")
    testImplementation("com.willowtreeapps.assertk:assertk")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.compileKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "16"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
        jvmTarget = "16"
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
