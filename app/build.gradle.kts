plugins {
    val kotlinVersion = "1.9.23"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "3.2.3"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "hu.kotlin.feladat.ms"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.ktor:ktor-client-okhttp:1.6.6")
    implementation("io.ktor:ktor-client-core:1.6.6")
    implementation("io.ktor:ktor-client-json:1.6.6")
    implementation("io.ktor:ktor-client-serialization:1.6.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.3") // Spring Cloud OpenFeign integration
    implementation("io.github.openfeign:feign-okhttp:11.2")
    implementation("io.github.openfeign:feign-jackson:11.2")
    implementation("org.reactivestreams:reactive-streams:1.0.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.3")
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.4.1")
    testImplementation("com.github.tomakehurst:wiremock-standalone:3.0.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}