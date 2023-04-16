plugins {
    id("org.gradle.base")
    id("java")
    id("jacoco")
    alias(libs.plugins.spring.boot).apply(false)
    alias(libs.plugins.kotlin.jvm).apply(false)
    alias(libs.plugins.kotlin.kapt).apply(false)
    alias(libs.plugins.kotlin.spring).apply(false)
    alias(libs.plugins.dokka).apply(false)
    alias(libs.plugins.gitProperties).apply(false)
}

subprojects {
    group = "md.vnastasi"
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
