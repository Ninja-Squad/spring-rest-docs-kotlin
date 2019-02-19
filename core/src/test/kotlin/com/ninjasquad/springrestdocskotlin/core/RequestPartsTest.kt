package com.ninjasquad.springrestdocskotlin.core

import com.ninjasquad.springrestdocskotlin.core.Snippets.requestParts
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.springframework.restdocs.request.RequestPartsSnippet

/**
 * Unit tests for request parts
 * @author JB Nizet
 */
class RequestPartsTest {

    @Test
    fun `should create a non-relaxed snippet without attributes by default`() {
        val snippet = requestParts {
        }

        assertThat(snippet.attributeMap).isEmpty()
        assertThat(snippet.relaxed).isFalse()
    }

    @Test
    fun `should create snippet with attributes`() {
        val snippet = requestParts(attributes = mapOf("foo" to "bar")) {
        }

        assertThat(snippet.attributeMap).containsOnly(entry("foo", "bar"))
    }

    @Test
    fun `should create relaxed snippet`() {
        val snippet = requestParts(relaxed = true) {
        }

        assertThat(snippet.relaxed).isTrue()
    }

    @Test
    fun `should add non-optional, non-ignored, no-attribute part by default`() {
        val snippet = requestParts {
            add("foo", "bar")
        }

        assertThat(snippet.descriptors).hasSize(1)
        val descriptor = snippet.descriptors["foo"]
        assertThat(descriptor?.description).isEqualTo("bar")
        assertThat(descriptor?.isOptional).isFalse()
        assertThat(descriptor?.isIgnored).isFalse()
        assertThat(descriptor?.attributes).isEmpty()
    }

    @Test
    fun `should add optional part`() {
        val snippet = requestParts {
            add("foo", "bar", optional = true)
        }

        assertThat(snippet.descriptors["foo"]?.isOptional).isTrue()
    }

    @Test
    fun `should add ignored part`() {
        val snippet = requestParts {
            add("foo", ignored = true)
        }

        val descriptor = snippet.descriptors["foo"]
        assertThat(descriptor?.isIgnored).isTrue()
        assertThat(descriptor?.description).isNull()
    }

    @Test
    fun `should add part with attributes`() {
        val snippet = requestParts {
            add("foo", "bar", attributes = mapOf("name" to "value"))
        }

        assertThat(snippet.descriptors["foo"]?.attributes).containsOnly(entry("name", "value"))
    }

    @Test
    fun `should add request parts to documentation scope`() {
        val documentationScope = documentationScope("test")
        documentationScope.requestParts {
        }

        assertThat(documentationScope.snippets.first()).isInstanceOf(RequestPartsSnippet::class.java)
    }

    @Test
    fun `should honor arguments when adding request parts to documentation scope`() {
        val documentationScope = documentationScope("test")
        documentationScope.requestParts(relaxed = true, attributes = mapOf("name" to "value")) {
            add("foo", "baz")
        }

        val snippet = documentationScope.snippets.first()
        assertThat(snippet).isInstanceOf(RequestPartsSnippet::class.java)

        snippet as RequestPartsSnippet
        assertThat(snippet.relaxed).isTrue()
        assertThat(snippet.attributeMap).containsOnly(entry("name", "value"))
        assertThat(snippet.descriptors).hasSize(1)
    }
}
