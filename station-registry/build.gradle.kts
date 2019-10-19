@file:Suppress("UnstableApiUsage")

version = "0.0.1-SNAPSHOT"

plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("com.squareup.okhttp3:mockwebserver")
    testImplementation("com.squareup.okhttp3:okhttp")
    testImplementation("com.squareup.okio:okio")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-common")

    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.processResources {
    filesMatching("bootstrap.yml") {
        expand(project.properties)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        csv.isEnabled = false
        xml.isEnabled = false
        html.isEnabled = true
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
