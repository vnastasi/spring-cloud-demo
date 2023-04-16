@file:Suppress("UnstableApiUsage")

version = "1.2.1"

plugins {
    id("java")
    id("jacoco")
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.gitProperties)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(platform(libs.spring.cloud.bom))
    implementation(platform(libs.spring.boot.bom))
    implementation(libs.spring.boot.webflux)
    implementation(libs.spring.boot.actuator)
    implementation(libs.spring.cloud.config.client)
    implementation(libs.spring.cloud.eureka.client)

    testImplementation(libs.spring.boot.test)
    testImplementation(libs.reactor.test)
    testImplementation(libs.junit5.api)
    testImplementation(libs.junit5.params)
    testImplementation(libs.junit5.engine)
    testImplementation(libs.mockito.junit5)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.okhttp.core)
    testImplementation(libs.okio)
    testImplementation(libs.kotlin.stdlib)
}

tasks.processResources {
    filesMatching("application.yml") {
        expand(project.properties)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        csv.required.set(false)
        xml.required.set(false)
        html.required.set(true)
    }

    sourceDirectories.setFrom(file("src/main/java"))
    classDirectories.setFrom(files("$buildDir/classes/java/main").asFileTree.matching {
        excludes += listOf(
            "**/StationRegistryApplication.*",
            "**/ApplicationConfig.*",
            "**/ApplicationProperties**.*",
            "**/model/**"
        )
    })
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