dependencies {
    api(project(":core"))
    api("org.springframework.restdocs:spring-restdocs-webtestclient")

    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
}
