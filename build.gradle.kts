import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.20"

    base
    kotlin("jvm") version kotlinVersion apply false
    id("org.jetbrains.dokka") version "0.9.17" apply false
    id("org.springframework.boot") version "2.1.2.RELEASE" apply false
}

version = "1.0.0-SNAPSHOT"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    group = "com.ninja-squad.spring-rest-docs-kotlin"
    version = rootProject.version
    description = "Kotlin DSL for Spring-REST-Docs ($name)"

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenCentral()
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
                jvmTarget = "1.8"
            }
        }

        withType<Test>() {
            useJUnitPlatform()
        }
    }

    dependencies {
        "implementation"(platform("org.springframework.boot:spring-boot-dependencies:2.1.2.RELEASE"))
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        "testImplementation"("org.junit.jupiter:junit-jupiter-api")
        "testImplementation"("org.junit.jupiter:junit-jupiter-params")
        "testImplementation"("org.assertj:assertj-core")
        "testImplementation"("org.jetbrains.kotlin:kotlin-reflect")

        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine")
    }
}
