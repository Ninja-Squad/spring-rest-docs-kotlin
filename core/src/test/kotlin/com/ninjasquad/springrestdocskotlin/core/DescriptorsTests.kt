package com.ninjasquad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.payload.JsonFieldType

/**
 * Tests for the factory methods of the [Descriptors] object
 * @author JB Nizet
 */
class DescriptorsTests {
    @Nested
    inner class Parameter {

        @Test
        fun `should create non-optional, non-ignored, no-attribute parameter descriptor by default`() {
            val descriptor = Descriptors.parameter("page", "the page number")

            assertThat(descriptor.name).isEqualTo("page")
            assertThat(descriptor.description).isEqualTo("the page number")
            assertThat(descriptor.isOptional).isFalse()
            assertThat(descriptor.isIgnored).isFalse()
            assertThat(descriptor.attributes).isEmpty()
        }

        @Test
        fun `should create an optional parameter descriptor`() {
            val descriptor = Descriptors.parameter("page", "the page number", optional = true)

            assertThat(descriptor.isOptional).isTrue()
        }

        @Test
        fun `should create an ignored parameter descriptor with no description`() {
            val descriptor = Descriptors.parameter("page", ignored = true)

            assertThat(descriptor.isIgnored).isTrue()
            assertThat(descriptor.description).isNull()
        }

        @Test
        fun `should create parameter descriptor with attributes`() {
            val descriptor = Descriptors.parameter(
                "page",
                "the page number",
                attributes = mapOf("a" to "b", "c" to null)
            )

            assertThat(descriptor.attributes).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Nested
    inner class Field {

        @Test
        fun `should create non-optional, non-ignored, untyped, no-attribute field descriptor by default`() {
            val descriptor = Descriptors.field("firstName", "the user first name")

            assertThat(descriptor.path).isEqualTo("firstName")
            assertThat(descriptor.description).isEqualTo("the user first name")
            assertThat(descriptor.isOptional).isFalse()
            assertThat(descriptor.isIgnored).isFalse()
            assertThat(descriptor.type).isNull()
            assertThat(descriptor.attributes).isEmpty()
        }

        @Test
        fun `should create an optional field descriptor`() {
            val descriptor = Descriptors.field("firstName", "the user first name", optional = true)

            assertThat(descriptor.isOptional).isTrue()
        }

        @Test
        fun `should create an ignored field descriptor with no description`() {
            val descriptor = Descriptors.field("firstName", ignored = true)

            assertThat(descriptor.isIgnored).isTrue()
            assertThat(descriptor.description).isNull()
        }

        @Test
        fun `should create field descriptor with type`() {
            val descriptor = Descriptors.field(
                "firstName",
                "the user first name",
                type = JsonFieldType.STRING
            )

            assertThat(descriptor.type).isEqualTo(JsonFieldType.STRING)
        }

        @Test
        fun `should create field descriptor with attributes`() {
            val descriptor = Descriptors.field(
                "firstName",
                "the user first name",
                attributes = mapOf("a" to "b", "c" to null)
            )

            assertThat(descriptor.attributes).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Nested
    inner class Subsection {

        @Test
        fun `should create non-optional, non-ignored, untyped, no-attribute subsection descriptor by default`() {
            val descriptor = Descriptors.subsection("address", "the user address")

            assertThat(descriptor.path).isEqualTo("address")
            assertThat(descriptor.description).isEqualTo("the user address")
            assertThat(descriptor.isOptional).isFalse()
            assertThat(descriptor.isIgnored).isFalse()
            assertThat(descriptor.type).isNull()
            assertThat(descriptor.attributes).isEmpty()
        }

        @Test
        fun `should create an optional subsection descriptor`() {
            val descriptor = Descriptors.subsection("address", "the user address", optional = true)

            assertThat(descriptor.isOptional).isTrue()
        }

        @Test
        fun `should create an ignored subsection descriptor with no description`() {
            val descriptor = Descriptors.subsection("address", ignored = true)

            assertThat(descriptor.isIgnored).isTrue()
            assertThat(descriptor.description).isNull()
        }

        @Test
        fun `should create subsection descriptor with type`() {
            val descriptor = Descriptors.subsection(
                "address",
                "the user address",
                type = JsonFieldType.STRING
            )

            assertThat(descriptor.type).isEqualTo(JsonFieldType.STRING)
        }

        @Test
        fun `should create subsection descriptor with attributes`() {
            val descriptor = Descriptors.subsection(
                "address",
                "the user address",
                attributes = mapOf("a" to "b", "c" to null)
            )

            assertThat(descriptor.attributes).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Nested
    inner class Header {

        @Test
        fun `should create non-optional, no-attribute header descriptor by default`() {
            val descriptor = Descriptors.header(HttpHeaders.AUTHORIZATION, "the basic auth credentials")

            assertThat(descriptor.name).isEqualTo(HttpHeaders.AUTHORIZATION)
            assertThat(descriptor.description).isEqualTo("the basic auth credentials")
            assertThat(descriptor.isOptional).isFalse()
            assertThat(descriptor.attributes).isEmpty()
        }

        @Test
        fun `should create an optional header descriptor`() {
            val descriptor = Descriptors.header(HttpHeaders.AUTHORIZATION, "the basic auth credentials", optional = true)

            assertThat(descriptor.isOptional).isTrue()
        }

        @Test
        fun `should create header descriptor with attributes`() {
            val descriptor = Descriptors.header(
                HttpHeaders.AUTHORIZATION,
                "the basic auth credentials",
                attributes = mapOf("a" to "b", "c" to null)
            )

            assertThat(descriptor.attributes).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Nested
    inner class RequestPart {

        @Test
        fun `should create non-optional, non-ignored, no-attribute request part descriptor by default`() {
            val descriptor = Descriptors.requestPart("file", "the uploaded image")

            assertThat(descriptor.name).isEqualTo("file")
            assertThat(descriptor.description).isEqualTo("the uploaded image")
            assertThat(descriptor.isOptional).isFalse()
            assertThat(descriptor.isIgnored).isFalse()
            assertThat(descriptor.attributes).isEmpty()
        }

        @Test
        fun `should create an optional request part descriptor`() {
            val descriptor = Descriptors.requestPart("file", "the uploaded image", optional = true)

            assertThat(descriptor.isOptional).isTrue()
        }

        @Test
        fun `should create an ignored request part descriptor with no description`() {
            val descriptor = Descriptors.requestPart("file", ignored = true)

            assertThat(descriptor.isIgnored).isTrue()
            assertThat(descriptor.description).isNull()
        }

        @Test
        fun `should create request part descriptor with attributes`() {
            val descriptor = Descriptors.requestPart(
                "file",
                "the uploaded image",
                attributes = mapOf("a" to "b", "c" to null)
            )

            assertThat(descriptor.attributes).containsOnly(entry("a", "b"), entry("c", null))
        }
    }

    @Nested
    inner class Link {

        @Test
        fun `should create non-optional, non-ignored, no-attribute link descriptor by default`() {
            val descriptor = Descriptors.link("next", "the URL of the next page")

            assertThat(descriptor.rel).isEqualTo("next")
            assertThat(descriptor.description).isEqualTo("the URL of the next page")
            assertThat(descriptor.isOptional).isFalse()
            assertThat(descriptor.isIgnored).isFalse()
            assertThat(descriptor.attributes).isEmpty()
        }

        @Test
        fun `should create an optional link descriptor`() {
            val descriptor = Descriptors.link("next", "the URL of the next page", optional = true)

            assertThat(descriptor.isOptional).isTrue()
        }

        @Test
        fun `should create an ignored link descriptor with no description`() {
            val descriptor = Descriptors.link("next", ignored = true)

            assertThat(descriptor.isIgnored).isTrue()
            assertThat(descriptor.description).isNull()
        }

        @Test
        fun `should create link descriptor with attributes`() {
            val descriptor = Descriptors.link(
                "next",
                "the URL of the next page",
                attributes = mapOf("a" to "b", "c" to null)
            )

            assertThat(descriptor.attributes).containsOnly(entry("a", "b"), entry("c", null))
        }
    }
}
