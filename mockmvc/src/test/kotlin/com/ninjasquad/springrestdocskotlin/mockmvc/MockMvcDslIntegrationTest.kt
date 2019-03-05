package com.ninjasquad.springrestdocskotlin.mockmvc

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder
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
 * Integration tests for the various docXxx extension methods and for andDocument
 * @author JB Nizet
 */
@ExtendWith(RestDocumentationExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MockMvcDslIntegrationTest {

    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(UserController())
            .apply<StandaloneMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun `should get user without preprocessors`() {
        mockMvc.docGet("/users/{userId}", 42)
            .andDo { print() }
            .andExpect {
                status { isOk }
            }.andDocument("users/get") {
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

        mockMvc.docGet("/users/{userId}", 42)
            .andExpect {
                status { isOk }
            }.andDocument("users/get-with-preprocessors") {
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
            arguments("post", HttpStatus.METHOD_NOT_ALLOWED, { mockMvc.docPost(uri) }),
            arguments("put", HttpStatus.METHOD_NOT_ALLOWED, { mockMvc.docPut(uri) }),
            arguments("delete", HttpStatus.METHOD_NOT_ALLOWED, { mockMvc.docDelete(uri) }),
            arguments("options", HttpStatus.OK, { mockMvc.docOptions(uri) }),
            arguments("head", HttpStatus.OK, { mockMvc.docHead(uri) }),
            arguments("patch", HttpStatus.METHOD_NOT_ALLOWED, { mockMvc.docPatch(uri) }),
            arguments("multipart", HttpStatus.METHOD_NOT_ALLOWED, { mockMvc.docMultipart(uri) }),
            arguments("request", HttpStatus.METHOD_NOT_ALLOWED, { mockMvc.docRequest(HttpMethod.POST, uri) })
        )
    }

    @ParameterizedTest(name = "doc{0}")
    @MethodSource("otherRequestMethodsArguments")
    fun `should support other methods`(method: String, expectedStatus: HttpStatus, request: () -> ResultActionsDsl) {
        request().andExpect {
            status { `is`(expectedStatus.value()) }
        }.andDocument("users/$method") { }

        val requestFile = File("build/generated-snippets/users/$method/http-request.adoc")
        assertThat(requestFile).exists()
    }
}
