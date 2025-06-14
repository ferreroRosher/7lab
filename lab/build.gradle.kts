import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

dependencies {
    implementation(project(":cli-core"))
    runtimeOnly(libs.postgresql)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.logback.classic)
    testImplementation("org.mockito:mockito-core:5.17.0")
}

tasks.test {
    useJUnitPlatform()
}

// Настройка ShadowJar
tasks.named<ShadowJar>("shadowJar") {
    // Убираем суффикс "-all", чтобы выходной файл был просто project-version.jar
    archiveClassifier.set("")
    // Опционально: объединяем файлы в META-INF/services
    mergeServiceFiles()
}

// Чтобы при `./gradlew build` собирался shadow JAR
tasks.named("build") {
    dependsOn("shadowJar")
}