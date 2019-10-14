plugins {
    id("org.gradle.base")
    id("java")
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
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
