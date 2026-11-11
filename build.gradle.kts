import java.text.SimpleDateFormat
import java.util.Date

plugins {
    `java-library`
    application
}

repositories {
    mavenCentral()
}

group = "org.example"
version = "1.0-SNAPSHOT"
description = "Project.Module2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.Main"
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
}

tasks.register("generateBuildInfo") {
    val projectVersion = project.version.toString()
    val outputDir = layout.buildDirectory.get().asFile
    val infoFile = file("$projectDir/build-info.txt")

    outputs.file(infoFile)

    doLast {
        outputDir.mkdirs()
        val buildDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        infoFile.writeText(
            """
            Version: $projectVersion
            Build date: $buildDate
            """.trimIndent()
        )
        println("Generated: ${infoFile.absolutePath}")
    }
}

tasks.named("build") {
    dependsOn("generateBuildInfo")
}
