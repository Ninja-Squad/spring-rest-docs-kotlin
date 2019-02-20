package com.ninjasquad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.headers.ResponseHeadersSnippet
import org.springframework.restdocs.hypermedia.HypermediaDocumentation
import org.springframework.restdocs.hypermedia.LinksSnippet
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestParametersSnippet
import org.springframework.restdocs.request.RequestPartsSnippet

/**
 * Tests for [DocumentationBuilder]
 * @author JB Nizet
 */
class DocumentationBuilderTests {
    private val scope = documentationScope("test")

    @Test
    fun `should have an identifier`() {
        assertThat(scope.identifier).isEqualTo("test")
    }

    @Test
    fun `should have no preprocessors by default`() {
        assertThat(scope.requestPreprocessor).isNull()
        assertThat(scope.responsePreprocessor).isNull()
    }

    @Test
    fun `should have no snippet by default`() {
        assertThat(scope.snippets).isEmpty()
    }

    @Test
    fun `should set preprocessors`() {
        val requestPreprocessor = Preprocessors.preprocessRequest(Preprocessors.prettyPrint())
        val responsePreprocessor = Preprocessors.preprocessResponse(Preprocessors.maskLinks())

        scope.requestPreprocessor = requestPreprocessor
        scope.responsePreprocessor = responsePreprocessor

        assertThat(scope.requestPreprocessor).isSameAs(requestPreprocessor)
        assertThat(scope.responsePreprocessor).isSameAs(responsePreprocessor)
    }

    @Test
    fun `should add reusable snippets`() {
        val pathParameters = Snippets.pathParameters {  }
        val responseFields = Snippets.responseFields {  }

        scope.snippet(pathParameters)
        scope.snippet(responseFields)

        assertThat(scope.snippets).containsExactly(pathParameters, responseFields)
    }

    @Test
    fun `should create and add path parameters with default options`() {
        scope.pathParameters {  }

        with(scope.snippets[0] as PathParametersSnippet) {
            assertThat(relaxed).isFalse()
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add path parameters with options`() {
        scope.pathParameters(
            relaxed = true,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("userId", "the user to get")
        }

        with(scope.snippets[0] as PathParametersSnippet) {
            assertThat(relaxed).isTrue()
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add request parameters with default options`() {
        scope.requestParameters {  }

        with(scope.snippets[0] as RequestParametersSnippet) {
            assertThat(relaxed).isFalse()
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add request parameters with options`() {
        scope.requestParameters(
            relaxed = true,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("userId", "the user to get")
        }

        with(scope.snippets[0] as RequestParametersSnippet) {
            assertThat(relaxed).isTrue()
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add request fields with default options`() {
        scope.requestFields {  }

        with(scope.snippets[0] as RequestFieldsSnippet) {
            assertThat(relaxed).isFalse()
            assertThat(payloadSubsectionExtractor).isNull()
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add request fields with options`() {
        val beneathUser = PayloadDocumentation.beneathPath("user")
        scope.requestFields(
            relaxed = true,
            subsectionExtractor = beneathUser,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("id", "the ID of the user")
        }

        with(scope.snippets[0] as RequestFieldsSnippet) {
            assertThat(relaxed).isTrue()
            assertThat(payloadSubsectionExtractor).isSameAs(beneathUser)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add response fields with default options`() {
        scope.responseFields {  }

        with(scope.snippets[0] as ResponseFieldsSnippet) {
            assertThat(relaxed).isFalse()
            assertThat(payloadSubsectionExtractor).isNull()
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add response fields with options`() {
        val beneathUser = PayloadDocumentation.beneathPath("user")
        scope.responseFields(
            relaxed = true,
            subsectionExtractor = beneathUser,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("id", "the ID of the user")
        }

        with(scope.snippets[0] as ResponseFieldsSnippet) {
            assertThat(relaxed).isTrue()
            assertThat(payloadSubsectionExtractor).isSameAs(beneathUser)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add request headers with default options`() {
        scope.requestHeaders {  }

        with(scope.snippets[0] as RequestHeadersSnippet) {
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add request headers with options`() {
        scope.requestHeaders(
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add(HttpHeaders.AUTHORIZATION, "the basic auth token")
        }

        with(scope.snippets[0] as RequestHeadersSnippet) {
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add response headers with default options`() {
        scope.responseHeaders {  }

        with(scope.snippets[0] as ResponseHeadersSnippet) {
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add response headers with options`() {
        scope.responseHeaders(
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add(HttpHeaders.CONTENT_TYPE, "the content type of the image")
        }

        with(scope.snippets[0] as ResponseHeadersSnippet) {
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add request parts with default options`() {
        scope.requestParts {  }

        with(scope.snippets[0] as RequestPartsSnippet) {
            assertThat(relaxed).isFalse()
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add request parts with options`() {
        scope.requestParts(
            relaxed = true,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("file", "the uploaded image")
        }

        with(scope.snippets[0] as RequestPartsSnippet) {
            assertThat(relaxed).isTrue()
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add request part fields with default options`() {
        scope.requestPartFields("form") {  }

        with(scope.snippets[0] as RequestPartFieldsSnippet) {
            assertThat(partName).isEqualTo("form")
            assertThat(relaxed).isFalse()
            assertThat(payloadSubsectionExtractor).isNull()
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add request part fields with options`() {
        val beneathUser = PayloadDocumentation.beneathPath("user")
        scope.requestPartFields(
            part = "form",
            relaxed = true,
            subsectionExtractor = beneathUser,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("id", "the ID of the user")
        }

        with(scope.snippets[0] as RequestPartFieldsSnippet) {
            assertThat(partName).isEqualTo("form")
            assertThat(relaxed).isTrue()
            assertThat(payloadSubsectionExtractor).isSameAs(beneathUser)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }

    @Test
    fun `should create and add request body with default options`() {
        scope.requestBody()

        with(scope.snippets[0] as RequestBodySnippet) {
            assertThat(payloadSubsectionExtractor).isNull()
            assertThat(attributeMap).isEmpty()
        }
    }

    @Test
    fun `should create and add request body with options`() {
        val beneathUser = PayloadDocumentation.beneathPath("user")
        scope.requestBody(
            subsectionExtractor = beneathUser,
            attributes = mapOf("a" to "b", "c" to null)
        )

        with(scope.snippets[0] as RequestBodySnippet) {
            assertThat(payloadSubsectionExtractor).isSameAs(beneathUser)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Test
    fun `should create and add response body with default options`() {
        scope.responseBody()

        with(scope.snippets[0] as ResponseBodySnippet) {
            assertThat(payloadSubsectionExtractor).isNull()
            assertThat(attributeMap).isEmpty()
        }
    }

    @Test
    fun `should create and add response body with options`() {
        val beneathUser = PayloadDocumentation.beneathPath("user")
        scope.responseBody(
            subsectionExtractor = beneathUser,
            attributes = mapOf("a" to "b", "c" to null)
        )

        with(scope.snippets[0] as ResponseBodySnippet) {
            assertThat(payloadSubsectionExtractor).isSameAs(beneathUser)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Test
    fun `should create and add request part body with default options`() {
        scope.requestPartBody("form")

        with(scope.snippets[0] as RequestPartBodySnippet) {
            assertThat(partName).isEqualTo("form")
            assertThat(payloadSubsectionExtractor).isNull()
            assertThat(attributeMap).isEmpty()
        }
    }

    @Test
    fun `should create and add request part body with options`() {
        val beneathUser = PayloadDocumentation.beneathPath("user")
        scope.requestPartBody(
            part = "form",
            subsectionExtractor = beneathUser,
            attributes = mapOf("a" to "b", "c" to null)
        )

        with(scope.snippets[0] as RequestPartBodySnippet) {
            assertThat(partName).isEqualTo("form")
            assertThat(payloadSubsectionExtractor).isSameAs(beneathUser)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Test
    fun `should create and add links with default options`() {
        scope.links {  }

        val defaultExtractor = HypermediaDocumentation.links().linkExtractor
        with(scope.snippets[0] as LinksSnippet) {
            assertThat(relaxed).isFalse()
            assertThat(linkExtractor).hasSameClassAs(defaultExtractor)
            assertThat(attributeMap).isEmpty()
            assertThat(descriptors).isEmpty()
        }
    }

    @Test
    fun `should create and add links with options`() {
        val atomLinksExtractor = HypermediaDocumentation.atomLinks()
        scope.links(
            relaxed = true,
            linkExtractor = atomLinksExtractor,
            attributes = mapOf("a" to "b", "c" to null)
        ) {
            add("next", "the URL of the next page")
        }

        with(scope.snippets[0] as LinksSnippet) {
            assertThat(relaxed).isTrue()
            assertThat(linkExtractor).isSameAs(linkExtractor)
            assertThat(attributeMap).containsOnly(entry("a", "b"), entry("c", null))
            assertThat(descriptors).hasSize(1)
        }
    }
}
