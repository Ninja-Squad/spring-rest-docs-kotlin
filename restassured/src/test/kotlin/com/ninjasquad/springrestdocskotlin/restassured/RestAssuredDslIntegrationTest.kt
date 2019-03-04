package com.ninjasquad.springrestdocskotlin.restassured

import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File


data class User(val id: Long, val firstName: String, val lastName: String)

@RestController
@RequestMapping("/users")
open class UserController {
    @GetMapping("/{userId}")
    fun get(@PathVariable userId: Long): User {
        return User(userId, "John", "Doe")
    }
}

/**
 * Integration tests for the andDocument extension method
 * @author JB Nizet
 */
@ExtendWith(RestDocumentationExtension::class)
@WebAppConfiguration
class RestAssuredDslIntegrationTest {

    private lateinit var documentationSpec: RequestSpecification
    private lateinit var tomcatServer: TomcatServer;

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        this.tomcatServer = TomcatServer().apply { start() }
        this.documentationSpec = RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build()
    }

    @AfterEach
    fun tearDown() {
        this.tomcatServer.stop()
    }

    @Test
    fun `should get user without preprocessors`() {
        given(documentationSpec)
            .port(tomcatServer.port)
            .andDocument("users/get") {
                pathParameters {
                    add("userId", "the ID of the user to get")
                }

                responseFields {
                    add("id", ignored = true)
                    add("firstName", "the user first name")
                    add("lastName", ignored = true)
                }
            }
            .get("/users/{userId}", 42)
            .then().statusCode(200)

        val pathParametersFile = File("build/generated-snippets/users/get/path-parameters.adoc")
        assertThat(pathParametersFile).exists()
        assertThat(pathParametersFile.readText()).contains("the ID of the user to get")

        val responseFieldsFile = File("build/generated-snippets/users/get/response-fields.adoc")
        assertThat(responseFieldsFile).exists()
        assertThat(responseFieldsFile.readText()).contains("the user first name")
    }
}
