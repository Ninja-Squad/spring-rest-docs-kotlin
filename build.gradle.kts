import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.3.20"

    base
    kotlin("jvm") version kotlinVersion apply false
    id("org.jetbrains.dokka") version "0.9.17"
}

version = "1.0.0-SNAPSHOT"

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "com.ninja-squad.spring-rest-docs-kotlin"
    version = rootProject.version
    description = "Kotlin DSL for Spring-REST-Docs ($name)"

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    repositories {
        mavenCentral()
        maven(url = uri("https://repo.spring.io/snapshot"))
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }

    dependencies {
        "implementation"(platform("org.springframework:spring-framework-bom:5.2.0.BUILD-SNAPSHOT"))
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        "testImplementation"("org.junit.jupiter:junit-jupiter:5.4.0")
        "testImplementation"("org.assertj:assertj-core:3.11.1")
        "testImplementation"("org.jetbrains.kotlin:kotlin-reflect")
    }
}

tasks {
    dokka {
        moduleName = rootProject.name
        outputFormat = "html"
        jdkVersion = 8

        val documentedSubProjects = subprojects.filter { it.name != "examples" }

        sourceDirs = files(documentedSubProjects.map { "${it.projectDir}/src/main/kotlin" })

        samples = listOf(
            "core/src/test/kotlin/com/ninjasquad/springrestdocskotlin/core/examples.kt",
            "mockmvc/src/test/kotlin/com/ninjasquad/springrestdocskotlin/mockmvc/examples.kt",
            "webtestclient/src/test/kotlin/com/ninjasquad/springrestdocskotlin/webtestclient/examples.kt",
            "restassured/src/test/kotlin/com/ninjasquad/springrestdocskotlin/restassured/examples.kt"
        )

        doFirst {
            classpath += files(documentedSubProjects.map { it.the<SourceSetContainer>()["main"].compileClasspath })
        }
    }
}
