dependencies {
    api(project(":core"))
    implementation("org.springframework:spring-webmvc")
    testImplementation("org.springframework:spring-test")
    testImplementation("org.apache.tomcat.embed:tomcat-embed-core:8.5.13")
    api("org.springframework.restdocs:spring-restdocs-restassured:2.0.4.BUILD-SNAPSHOT")
}
