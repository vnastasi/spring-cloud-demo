@file:Suppress("UnstableApiUsage")

buildscript {
    repositories {
        maven { setUrl("https://plugins.gradle.org/m2/0") }
        mavenLocal()
        mavenCentral()
        jcenter()
    }
}

plugins {
    id("org.gradle.base")
    id("java")
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    id("org.jetbrains.kotlin.kapt") version "1.4.20"
    id("org.jetbrains.kotlin.plugin.spring") version "1.4.20"
    id("org.jetbrains.dokka") version "0.10.1"
    id("com.gorylenko.gradle-git-properties") version "2.2.2"
    id("jacoco")
}

allprojects {
    group = "md.vnastasi"
    repositories {
        mavenLocal()
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.gradle.base")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.gorylenko.gradle-git-properties")
    apply(plugin = "jacoco")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR5")
        }

        dependencies {
            dependency("org.jetbrains.kotlin:kotlin-stdlib:1.4.20")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-common:1.4.20")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.3.9")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.9")

            dependency("com.squareup.okhttp3:mockwebserver:4.5.0")
            dependency("com.squareup.okhttp3:okhttp:4.5.0")
            dependency("com.squareup.okio:okio:2.5.0")

            dependency("org.mockito:mockito-junit-jupiter:3.3.3")
            dependency("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
            dependency("com.willowtreeapps.assertk:assertk-jvm:0.23")
        }
    }

    jacoco {
        toolVersion = "0.8.6"
    }
}

tasks.getByName("clean") {
    subprojects.forEach { project ->
        project.afterEvaluate {
            tasks.findByName("clean")?.also { this@getByName.dependsOn(it) }
        }
    }
}

tasks.register(name = "release", type = Zip::class) {
    group = "build"

    dependsOn(project.tasks.getByName("clean"))
    subprojects.mapNotNull { it.tasks.findByName("assemble") }.forEach { this@register.dependsOn(it) }

    from("configs") { into("configs") }
    from("scripts")
    from(subprojects.map { it.buildDir.resolve("libs") })

    archiveBaseName.set(rootProject.name)
    archiveVersion.set(timestamp())

    destinationDirectory.set(file("./"))
}
