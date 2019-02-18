package com.ninja_squad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.springframework.restdocs.request.RequestParametersSnippet

/**
 * Unit tests for request parameters
 * @author JB Nizet
 */
class RequestParametersTest {

    @Test
    fun `should create a non-relaxed snippet without attributes by default`() {
        val snippet = requestParametersSnippet {
        }

        assertThat(snippet.attributeMap).isEmpty()
        assertThat(snippet.relaxed).isFalse()
    }

    @Test
    fun `should create snippet with attributes`() {
        val snippet = requestParametersSnippet(attributes = mapOf("foo" to "bar")) {
        }

        assertThat(snippet.attributeMap).containsOnly(entry("foo", "bar"))
    }

    @Test
    fun `should create relaxed snippet`() {
        val snippet = requestParametersSnippet(relaxed = true) {
        }

        assertThat(snippet.relaxed).isTrue()
    }

    @Test
    fun `should add non-optional, non-ignored, no-attribute parameter by default`() {
        val snippet = requestParametersSnippet {
            add(parameter("foo", "bar"))
        }

        assertThat(snippet.descriptors).hasSize(1)
        val parameterDescriptor = snippet.descriptors["foo"]
        assertThat(parameterDescriptor?.description).isEqualTo("bar")
        assertThat(parameterDescriptor?.isOptional).isFalse()
        assertThat(parameterDescriptor?.isIgnored).isFalse()
        assertThat(parameterDescriptor?.attributes).isEmpty()
    }

    @Test
    fun `should add optional parameter`() {
        val snippet = requestParametersSnippet {
            add("foo", "bar", optional = true)
        }

        assertThat(snippet.descriptors["foo"]?.isOptional).isTrue()
    }

    @Test
    fun `should add ignored parameter`() {
        val snippet = requestParametersSnippet {
            add("foo", ignored = true)
        }

        val parameterDescriptor = snippet.descriptors["foo"]
        assertThat(parameterDescriptor?.isIgnored).isTrue()
        assertThat(parameterDescriptor?.description).isNull()
    }

    @Test
    fun `should add parameter with attributes`() {
        val snippet = requestParametersSnippet {
            add("foo", "bar", attributes = mapOf("name" to "value"))
        }

        assertThat(snippet.descriptors["foo"]?.attributes).containsOnly(entry("name", "value"))
    }

    @Test
    fun `should add path parameters to documentation scope`() {
        val documentationScope = documentationScope("test")
        documentationScope.requestParameters {
        }

        assertThat(documentationScope.snippets.first()).isInstanceOf(RequestParametersSnippet::class.java)
    }

    @Test
    fun `should honor arguments when adding path parameters to documentation scope`() {
        val documentationScope = documentationScope("test")
        documentationScope.requestParameters(relaxed = true, attributes = mapOf("name" to "value")) {
            add("foo", "baz")
        }

        val snippet = documentationScope.snippets.first()
        assertThat(snippet).isInstanceOf(RequestParametersSnippet::class.java)

        snippet as RequestParametersSnippet
        assertThat(snippet.relaxed).isTrue()
        assertThat(snippet.attributeMap).containsOnly(entry("name", "value"))
        assertThat(snippet.descriptors).hasSize(1)
    }
}
