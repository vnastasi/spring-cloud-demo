pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { setUrl("https://repo.spring.io/milestone") }
    }
}

rootProject.name = "spring-cloud-demo"

include(
    ":config-server",
    ":eureka-server",
    ":api-gateway",
    ":station-registry",
    ":timetable",
    ":disturbances"
)
