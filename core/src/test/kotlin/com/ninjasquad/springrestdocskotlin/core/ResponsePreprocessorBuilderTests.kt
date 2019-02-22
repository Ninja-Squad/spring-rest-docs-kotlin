package com.ninjasquad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.operation.OperationResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors
import java.nio.charset.StandardCharsets

/**
 * Unit tests for [ResponsePreprocessorBuilder]
 * @author JB Nizet
 */
class ResponsePreprocessorBuilderTests {

    private val builder = ResponsePreprocessorBuilder()

    @Test
    fun `should add a pretty print preprocessor`() {
        val inputResponse = fakeResponse(content = """ {"id":1} """.toByteArray())
        val preprocessedResponse = builder.apply { prettyPrint() }.build().preprocess(inputResponse)
        assertThat(preprocessedResponse.contentAsString).isEqualTo(
            """
                {
                  "id" : 1
                }""".trimIndent()
        )
    }

    @Test
    fun `should remove headers`() {
        val inputResponse = fakeResponse(
            headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                contentLength = 100
                accept = listOf(MediaType.ALL)
            }
        )
        val preprocessedResponse = builder.apply {
            removeHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.CONTENT_LENGTH)
        }
            .build()
            .preprocess(inputResponse)

        assertThat(preprocessedResponse.headers).containsOnlyKeys(HttpHeaders.ACCEPT)
    }

    @Test
    fun `should remove matching headers`() {
        val inputResponse = fakeResponse(
            headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                contentLength = 100
                accept = listOf(MediaType.ALL)
                pragma = "nocache"
            }
        )
        val preprocessedResponse = builder.apply {
            removeMatchingHeaders(Regex("Content.*"), Regex(".*cept"))
        }
            .build()
            .preprocess(inputResponse)

        assertThat(preprocessedResponse.headers).containsOnlyKeys(HttpHeaders.PRAGMA)
    }

    @Test
    fun `should mask links with default mask`() {
        val inputResponse = fakeResponse(
            content = """{ "href" : "http://google.com" }""".toByteArray()
        )
        val preprocessedResponse = builder.apply { maskLinks() }.build().preprocess(inputResponse)

        assertThat(preprocessedResponse.contentAsString).isEqualTo("""{ "href" : "..." }""")
    }

    @Test
    fun `should mask links with custom mask`() {
        val inputResponse = fakeResponse(
            content = """{ "href" : "http://google.com" }""".toByteArray()
        )
        val preprocessedResponse = builder.apply { maskLinks("TEST") }.build().preprocess(inputResponse)

        assertThat(preprocessedResponse.contentAsString).isEqualTo("""{ "href" : "TEST" }""")
    }

    @Test
    fun `should replace regex`() {
        val inputResponse = fakeResponse(
            content = "hello world!".toByteArray()
        )
        val preprocessedResponse = builder.apply { replaceRegex(Regex("w.*d"), "earth") }.build().preprocess(inputResponse)

        assertThat(preprocessedResponse.contentAsString).isEqualTo("hello earth!")
    }

    @Test
    fun `should modify URIs`() {
        val inputResponse = fakeResponse(
            content = """<a href="http://localhost/actuator">actuator</a>""".toByteArray()
        )
        val preprocessedResponse = builder.apply {
            modifyUris {
                scheme("https")
            }
        }.build().preprocess(inputResponse)

        assertThat(preprocessedResponse.contentAsString).isEqualTo(
            """<a href="https://localhost/actuator">actuator</a>"""
        )
    }

    @Test
    fun `should add a reusable preprocessor`() {
        val reusablePreprocessor = Preprocessors.removeHeaders(HttpHeaders.CONTENT_LENGTH)

        val inputResponse = fakeResponse(
            headers = HttpHeaders().apply { contentLength = 100 }
        )
        val preprocessedResponse = builder.apply { add(reusablePreprocessor) }.build().preprocess(inputResponse)

        assertThat(preprocessedResponse.headers).isEmpty()
    }
}

fun fakeResponse(
    status: HttpStatus = HttpStatus.OK,
    headers: HttpHeaders = HttpHeaders.EMPTY,
    content: ByteArray = ByteArray(0)
): OperationResponse =
    object : OperationResponse {
        override fun getStatus(): HttpStatus = status
        override fun getHeaders(): HttpHeaders = headers
        override fun getContentAsString(): String = content.toString(StandardCharsets.UTF_8)
        override fun getContent(): ByteArray = content
    }
