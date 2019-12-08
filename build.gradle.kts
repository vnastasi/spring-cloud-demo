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
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    id("org.jetbrains.kotlin.kapt") version "1.3.61"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.61"
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.gorylenko.gradle-git-properties") version "2.2.0"
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
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.M3")
        }

        dependencies {
            dependency("org.jetbrains.kotlin:kotlin-stdlib:1.3.61")
            dependency("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.61")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
            dependency("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.2")

            dependency("com.squareup.okhttp3:mockwebserver:4.2.2")
            dependency("com.squareup.okhttp3:okhttp:4.2.2")
            dependency("com.squareup.okio:okio:2.4.1")

            dependency("org.mockito:mockito-junit-jupiter:3.2.0")
            dependency("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
        }
    }
}

tasks.register(name = "release", type = Zip::class) {
    group = "build"
    dependsOn(":api-gateway:assemble", ":config-server:assemble", ":eureka-server:assemble", ":station-registry:assemble", ":timetable:assemble")
    from(
        project("api-gateway").buildDir.resolve("libs"),
        project("config-server").buildDir.resolve("libs"),
        project("eureka-server").buildDir.resolve("libs"),
        project("station-registry").buildDir.resolve("libs"),
        project("timetable").buildDir.resolve("libs")
    )
    from("configs") { into("configs") }
    archiveBaseName.set(rootProject.name)
    archiveVersion.set(timestamp())
    destinationDirectory.set(file("./"))
}
