package com.ninja_squad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Test
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.beneathPath
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.SubsectionDescriptor

/**
 * Unit tests for request fields
 * @author JB Nizet
 */
class RequestFieldsTest {

    @Test
    fun `should create a non-relaxed snippet without attributes, without subsection extractor by default`() {
        val snippet = requestFieldsSnippet {
        }

        assertThat(snippet.attributeMap).isEmpty()
        assertThat(snippet.relaxed).isFalse()
        assertThat(snippet.payloadSubsectionExtractor).isNull()
    }

    @Test
    fun `should create snippet with subsection extractor`() {
        val subsectionExtractor = beneathPath("body")
        val snippet = requestFieldsSnippet(subsectionExtractor = subsectionExtractor) {
        }

        assertThat(snippet.payloadSubsectionExtractor).isSameAs(subsectionExtractor)
    }

    @Test
    fun `should create snippet with attributes`() {
        val snippet = requestFieldsSnippet(attributes = mapOf("foo" to "bar")) {
        }

        assertThat(snippet.attributeMap).containsOnly(entry("foo", "bar"))
    }

    @Test
    fun `should create relaxed snippet`() {
        val snippet = requestFieldsSnippet(relaxed = true) {
        }

        assertThat(snippet.relaxed).isTrue()
    }

    @Test
    fun `should add non-optional, non-ignored, not typed, no-attribute field by default`() {
        val snippet = requestFieldsSnippet {
            add("foo", "bar")
        }

        assertThat(snippet.descriptors).hasSize(1)
        val descriptor = snippet.descriptors.first()
        assertThat(descriptor.path).isEqualTo("foo")
        assertThat(descriptor.description).isEqualTo("bar")
        assertThat(descriptor.isOptional).isFalse()
        assertThat(descriptor.isIgnored).isFalse()
        assertThat(descriptor.type).isNull()
        assertThat(descriptor.attributes).isEmpty()
    }

    @Test
    fun `should add optional field`() {
        val snippet = requestFieldsSnippet {
            add("foo", "bar", optional = true)
        }

        assertThat(snippet.descriptors.first().isOptional).isTrue()
    }

    @Test
    fun `should add ignored field`() {
        val snippet = requestFieldsSnippet {
            add("foo", ignored = true)
        }

        val descriptor = snippet.descriptors.first()
        assertThat(descriptor.isIgnored).isTrue()
        assertThat(descriptor.description).isNull()
    }

    @Test
    fun `should add typed field`() {
        val snippet = requestFieldsSnippet {
            add("foo", "bar", type = JsonFieldType.NUMBER)
        }

        val descriptor = snippet.descriptors.first()
        assertThat(descriptor.type).isEqualTo(JsonFieldType.NUMBER)
    }

    @Test
    fun `should add field with attributes`() {
        val snippet = requestFieldsSnippet {
            add("foo", "bar", attributes = mapOf("name" to "value"))
        }

        assertThat(snippet.descriptors[0].attributes).containsOnly(entry("name", "value"))
    }

    @Test
    fun `should add field with prefix`() {
        val snippet = requestFieldsSnippet {
            withPrefix("user.") {
                add("name", "the user name")
            }
        }

        val descriptor = snippet.descriptors[0]
        assertThat(descriptor.path).isEqualTo("user.name")
        assertThat(descriptor.description).isEqualTo("the user name")
    }

    @Test
    fun `should add non-optional, non-ignored, not typed, no-attribute subsection by default`() {
        val snippet = requestFieldsSnippet {
            addSubsection("foo", "bar")
        }

        assertThat(snippet.descriptors).hasSize(1)
        val descriptor = snippet.descriptors.first()
        assertThat(descriptor).isInstanceOf(SubsectionDescriptor::class.java)
        assertThat(descriptor.path).isEqualTo("foo")
        assertThat(descriptor.description).isEqualTo("bar")
        assertThat(descriptor.isOptional).isFalse()
        assertThat(descriptor.isIgnored).isFalse()
        assertThat(descriptor.type).isNull()
        assertThat(descriptor.attributes).isEmpty()
    }

    @Test
    fun `should add optional subsection`() {
        val snippet = requestFieldsSnippet {
            addSubsection("foo", "bar", optional = true)
        }

        assertThat(snippet.descriptors.first().isOptional).isTrue()
    }

    @Test
    fun `should add ignored subsection`() {
        val snippet = requestFieldsSnippet {
            addSubsection("foo", ignored = true)
        }

        val descriptor = snippet.descriptors.first()
        assertThat(descriptor.isIgnored).isTrue()
        assertThat(descriptor.description).isNull()
    }

    @Test
    fun `should add typed subsection`() {
        val snippet = requestFieldsSnippet {
            addSubsection("foo", "bar", type = "geocoordinates")
        }

        val descriptor = snippet.descriptors.first()
        assertThat(descriptor.type).isEqualTo("geocoordinates")
    }

    @Test
    fun `should add subsection with attributes`() {
        val snippet = requestFieldsSnippet {
            addSubsection("foo", "bar", attributes = mapOf("name" to "value"))
        }

        assertThat(snippet.descriptors[0].attributes).containsOnly(entry("name", "value"))
    }

    @Test
    fun `should add subsection with prefix`() {
        val snippet = requestFieldsSnippet {
            withPrefix("user.") {
                addSubsection("location", "the user location")
            }
        }

        val descriptor = snippet.descriptors[0]
        assertThat(descriptor.path).isEqualTo("user.location")
        assertThat(descriptor.description).isEqualTo("the user location")
        assertThat(descriptor).isInstanceOf(SubsectionDescriptor::class.java)
    }

    @Test
    fun `should add request field to documentation scope`() {
        val documentationScope = documentationScope("test")
        documentationScope.requestFields {
        }

        assertThat(documentationScope.snippets.first()).isInstanceOf(RequestFieldsSnippet::class.java)
    }

    @Test
    fun `should honor arguments when adding request fields to documentation scope`() {
        val documentationScope = documentationScope("test")
        val subsectionExtractor = beneathPath("body")
        documentationScope.requestFields(
            relaxed = true,
            subsectionExtractor = subsectionExtractor,
            attributes = mapOf("name" to "value")
        ) {
            add("foo", "baz")
        }

        val snippet = documentationScope.snippets.first()
        assertThat(snippet).isInstanceOf(RequestFieldsSnippet::class.java)

        snippet as RequestFieldsSnippet
        assertThat(snippet.relaxed).isTrue()
        assertThat(snippet.payloadSubsectionExtractor).isSameAs(subsectionExtractor)
        assertThat(snippet.attributeMap).containsOnly(entry("name", "value"))
        assertThat(snippet.descriptors).hasSize(1)
    }
}
