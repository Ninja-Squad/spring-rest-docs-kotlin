package com.ninja_squad.springrestdocskotlin.mockmvc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
 * Integration tests for the various docXxx extension methods and for andDocument
 * @author JB Nizet
 */
@WebMvcTest(UserController::class)
@ExtendWith(RestDocumentationExtension::class)
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MockMvcDslIntegrationTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `should get user without preprocessors`() {
        mockMvc.perform(docGet("/users/{userId}", 42))
            .andExpect(status().isOk)
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

        val pathParametersFile = File("build/generated-snippets/users/get/path-parameters.adoc")
        assertThat(pathParametersFile).exists()
        assertThat(pathParametersFile.readText()).contains("the ID of the user to get")

        val responseFieldsFile = File("build/generated-snippets/users/get/response-fields.adoc")
        assertThat(responseFieldsFile).exists()
        assertThat(responseFieldsFile.readText()).contains("the user first name")
    }

    @Test
    fun `should get user with preprocessors`() {
        var requestPreprocessed = false
        val fakeRequestPreprocessor = OperationRequestPreprocessor { request ->
            requestPreprocessed = true
            request
        }

        var responsePreprocessed = false
        val fakeResponsePreprocessor = OperationResponsePreprocessor { response ->
            responsePreprocessed = true
            response
        }

        mockMvc.perform(docGet("/users/{userId}", 42))
            .andExpect(status().isOk)
            .andDocument("users/get-with-preprocessors") {
                requestPreprocessor = fakeRequestPreprocessor
                responsePreprocessor = fakeResponsePreprocessor
            }

        val requestFile = File("build/generated-snippets/users/get-with-preprocessors/http-request.adoc")
        assertThat(requestFile).exists()
        assertThat(requestPreprocessed).isTrue()
        assertThat(responsePreprocessed).isTrue()
    }

    fun otherRequestMethodsArguments(): List<Arguments> {
        val uri = "/users/42"
        return listOf(
            arguments("post", docPost(uri), HttpStatus.METHOD_NOT_ALLOWED),
            arguments("put", docPut(uri), HttpStatus.METHOD_NOT_ALLOWED),
            arguments("delete", docDelete(uri), HttpStatus.METHOD_NOT_ALLOWED),
            arguments("options", docOptions(uri), HttpStatus.OK),
            arguments("head", docHead(uri), HttpStatus.OK),
            arguments("patch", docPatch(uri), HttpStatus.METHOD_NOT_ALLOWED),
            arguments("fileUpload", docFileUpload(uri), HttpStatus.METHOD_NOT_ALLOWED),
            arguments("request", docRequest(HttpMethod.POST, uri), HttpStatus.METHOD_NOT_ALLOWED)
        )
    }

    @ParameterizedTest
    @MethodSource("otherRequestMethodsArguments")
    fun `should post`(method: String, mockHttpServletRequestBuilder: MockHttpServletRequestBuilder, expectedStatus: HttpStatus) {
        mockMvc.perform(mockHttpServletRequestBuilder)
            .andExpect(status().`is`(expectedStatus.value()))
            .andDocument("users/$method") {
            }

        val requestFile = File("build/generated-snippets/users/$method/http-request.adoc")
        assertThat(requestFile).exists()
    }
}
