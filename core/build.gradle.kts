dependencies {
    api("org.springframework.restdocs:spring-restdocs-core")
}

tasks {
    dokka {
        samples = listOf("src/test/kotlin/com/ninjasquad/springrestdocskotlin/core/examples.kt")
    }
}