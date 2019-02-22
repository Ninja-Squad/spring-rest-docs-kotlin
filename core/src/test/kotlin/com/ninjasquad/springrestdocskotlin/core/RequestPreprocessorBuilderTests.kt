package com.ninjasquad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.operation.OperationRequest
import org.springframework.restdocs.operation.OperationRequestPart
import org.springframework.restdocs.operation.Parameters
import org.springframework.restdocs.operation.RequestCookie
import org.springframework.restdocs.operation.preprocess.Preprocessors
import java.net.URI
import java.nio.charset.StandardCharsets

/**
 * Unit tests for [RequestPreprocessorBuilder]
 * @author JB Nizet
 */
class RequestPreprocessorBuilderTests {

    private val builder = RequestPreprocessorBuilder()

    @Test
    fun `should add a pretty print preprocessor`() {
        val inputRequest = fakeRequest(content = """ {"id":1} """.toByteArray())
        val preprocessedRequest = builder.apply { prettyPrint() }.build().preprocess(inputRequest)
        assertThat(preprocessedRequest.contentAsString).isEqualTo(
            """
                {
                  "id" : 1
                }""".trimIndent()
        )
    }

    @Test
    fun `should remove headers`() {
        val inputRequest = fakeRequest(
            headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                contentLength = 100
                accept = listOf(MediaType.ALL)
            }
        )
        val preprocessedRequest = builder.apply {
            removeHeaders(HttpHeaders.CONTENT_TYPE, HttpHeaders.CONTENT_LENGTH)
        }
            .build()
            .preprocess(inputRequest)

        assertThat(preprocessedRequest.headers).containsOnlyKeys(HttpHeaders.ACCEPT)
    }

    @Test
    fun `should remove matching headers`() {
        val inputRequest = fakeRequest(
            headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                contentLength = 100
                accept = listOf(MediaType.ALL)
                pragma = "nocache"
            }
        )
        val preprocessedRequest = builder.apply {
            removeMatchingHeaders(Regex("Content.*"), Regex(".*cept"))
        }
            .build()
            .preprocess(inputRequest)

        assertThat(preprocessedRequest.headers).containsOnlyKeys(HttpHeaders.PRAGMA)
    }

    @Test
    fun `should mask links with default mask`() {
        val inputRequest = fakeRequest(
            content = """{ "href" : "http://google.com" }""".toByteArray()
        )
        val preprocessedRequest = builder.apply { maskLinks() }.build().preprocess(inputRequest)

        assertThat(preprocessedRequest.contentAsString).isEqualTo("""{ "href" : "..." }""")
    }

    @Test
    fun `should mask links with custom mask`() {
        val inputRequest = fakeRequest(
            content = """{ "href" : "http://google.com" }""".toByteArray()
        )
        val preprocessedRequest = builder.apply { maskLinks("TEST") }.build().preprocess(inputRequest)

        assertThat(preprocessedRequest.contentAsString).isEqualTo("""{ "href" : "TEST" }""")
    }

    @Test
    fun `should replace regex`() {
        val inputRequest = fakeRequest(
            content = "hello world!".toByteArray()
        )
        val preprocessedRequest = builder.apply { replaceRegex(Regex("w.*d"), "earth") }.build().preprocess(inputRequest)

        assertThat(preprocessedRequest.contentAsString).isEqualTo("hello earth!")
    }

    @Test
    fun `should modify URIs`() {
        val inputRequest = fakeRequest(
            content = """<a href="http://localhost/actuator">actuator</a>""".toByteArray()
        )
        val preprocessedRequest = builder.apply {
            modifyUris {
                scheme("https")
            }
        }.build().preprocess(inputRequest)

        assertThat(preprocessedRequest.contentAsString).isEqualTo(
            """<a href="https://localhost/actuator">actuator</a>"""
        )
    }

    @Test
    fun `should modify parameters`() {
        val inputRequest = fakeRequest()
        val preprocessedRequest = builder.apply {
            modifyParameters {
                add("foo", "bar")
            }
        }.build().preprocess(inputRequest)

        assertThat(preprocessedRequest.parameters).containsOnly(entry("foo", listOf("bar")))
    }

    @Test
    fun `should add a reusable preprocessor`() {
        val reusablePreprocessor = Preprocessors.modifyParameters().add("foo", "bar")

        val inputRequest = fakeRequest()
        val preprocessedRequest = builder.apply { add(reusablePreprocessor) }.build().preprocess(inputRequest)

        assertThat(preprocessedRequest.parameters).containsOnly(entry("foo", listOf("bar")))
    }
}

fun fakeRequest(
    uri: URI = URI("/"),
    method: HttpMethod = HttpMethod.POST,
    content: ByteArray = ByteArray(0),
    headers: HttpHeaders = HttpHeaders.EMPTY,
    parameters: Parameters = Parameters(),
    parts: Collection<OperationRequestPart> = emptyList(),
    cookies: Collection<RequestCookie> = emptyList()
): OperationRequest =
    object : OperationRequest {
        override fun getHeaders(): HttpHeaders = headers
        override fun getContentAsString(): String = content.toString(StandardCharsets.UTF_8)
        override fun getContent(): ByteArray = content
        override fun getParts(): Collection<OperationRequestPart> = parts
        override fun getParameters(): Parameters = parameters
        override fun getCookies(): Collection<RequestCookie> = cookies
        override fun getMethod(): HttpMethod = method
        override fun getUri(): URI = uri
    }
