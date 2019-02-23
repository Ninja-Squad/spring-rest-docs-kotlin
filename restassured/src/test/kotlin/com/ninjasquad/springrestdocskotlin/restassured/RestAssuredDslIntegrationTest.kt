package com.ninjasquad.springrestdocskotlin.restassured

import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

data class User(val id: Long, val firstName: String, val lastName: String)

@SpringBootApplication
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension::class)
@AutoConfigureRestDocs
class RestAssuredDslIntegrationTest(
    @LocalServerPort private val port: Int,
    @Autowired private val documentationSpec: RequestSpecification
) {

    @Test
    fun `should get user without preprocessors`() {
        given(documentationSpec)
            .port(port)
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
