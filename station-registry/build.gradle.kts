apply(plugin = "com.gorylenko.gradle-git-properties")

version = "0.0.1-SNAPSHOT"

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
    testImplementation("org.mockito:mockito-junit-jupiter:2.18.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.2.2")
    testImplementation("com.squareup.okhttp3:okhttp:4.2.2")
    testImplementation("com.squareup.okio:okio:2.4.1")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.50")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-common:1.3.50")

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