version = "0.0.1-SNAPSHOT"

plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
	implementation("org.springframework.cloud:spring-cloud-config-server")
}
