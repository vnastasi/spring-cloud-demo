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
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    id("org.jetbrains.kotlin.kapt") version "1.3.50"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.50"
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.gorylenko.gradle-git-properties") version "1.5.1"
    id("jacoco")
}

allprojects {
    group = "md.vnastasi"
    repositories {
        mavenLocal()
        mavenCentral()
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
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.SR3")
        }

        dependencies {
            dependency("org.jetbrains.kotlin:kotlin-stdlib:1.3.50")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.50")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.2")

            dependency("com.squareup.okhttp3:mockwebserver:4.2.2")
            dependency("com.squareup.okhttp3:okhttp:4.2.2")
            dependency("com.squareup.okio:okio:2.4.1")

            dependency("org.mockito:mockito-junit-jupiter:2.18.3")
        }
    }
}
