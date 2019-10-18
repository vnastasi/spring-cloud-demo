buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        maven {
            setUrl("https://plugins.gradle.org/m2/0")
        }
    }
}

plugins {
    id("org.gradle.base")
    id("java")
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
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
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.gorylenko.gradle-git-properties")
    apply(plugin = "jacoco")

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:Greenwich.SR3")
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
