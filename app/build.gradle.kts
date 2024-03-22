import java.net.URI

plugins {
    val kotlinVersion = "1.9.23"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "3.2.3"
    id("com.vaadin") version "24.3.7"
}

group = "hu.kotlin.feladat.ms"

repositories {
    mavenCentral()
    maven { url = URI("https://maven.vaadin.com/vaadin-addons") }
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.vaadin:vaadin-core:24.+")
    implementation("com.vaadin:vaadin-spring-boot-starter:24.+")
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

defaultTasks("clean", "vaadinBuildFrontend", "build")

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

vaadin {
    optimizeBundle = false
}