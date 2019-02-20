package com.ninjasquad.springrestdocskotlin.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.hypermedia.HypermediaDocumentation
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.SubsectionDescriptor

/**
 * Tests for the factory methods of the [Snippets] object
 * @author JB Nizet
 */
class SnippetsTests {
    @Nested
    inner class PathParameters {
        @Test
        fun `should create a non-relaxed, no-attribute path parameters snippet by default`() {
            val snippet = Snippets.pathParameters {
            }

            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed path parameters snippet`() {
            val snippet = Snippets.pathParameters(relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a path parameters snippet with attributes`() {
            val snippet = Snippets.pathParameters(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a path parameters snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.parameter("id", "the user ID")
            val snippet = Snippets.pathParameters {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors["id"]).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a path parameters snippet with parameters defined inline`() {
            val snippet = Snippets.pathParameters {
                add("id", "the user ID", optional = true, attributes = mapOf("a" to "b", "c" to null))
                add("dummy", ignored = true)
            }

            with(snippet.descriptors["id"]!!) {
                assertThat(name).isEqualTo("id")
                assertThat(description).isEqualTo("the user ID")
                assertThat(isOptional).isTrue()
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors["dummy"]?.isIgnored).isTrue()
        }
    }

    @Nested
    inner class RequestParameters {
        @Test
        fun `should create a non-relaxed, no-attribute request parameters snippet by default`() {
            val snippet = Snippets.requestParameters {
            }

            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed request parameters snippet`() {
            val snippet = Snippets.requestParameters(relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a request parameters snippet with attributes`() {
            val snippet = Snippets.requestParameters(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request parameters snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.parameter("page", "the page number")
            val snippet = Snippets.requestParameters {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors["page"]).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a request parameters snippet with parameters defined inline`() {
            val snippet = Snippets.requestParameters {
                add("page", "the page number", optional = true, attributes = mapOf("a" to "b", "c" to null))
                add("dummy", ignored = true)
            }

            with(snippet.descriptors["page"]!!) {
                assertThat(name).isEqualTo("page")
                assertThat(description).isEqualTo("the page number")
                assertThat(isOptional).isTrue()
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors["dummy"]?.isIgnored).isTrue()
        }
    }

    @Nested
    inner class RequestFields {
        @Test
        fun `should create a non-relaxed, no-attribute, no subsection-extractor request fields snippet by default`() {
            val snippet = Snippets.requestFields {
            }

            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.payloadSubsectionExtractor).isNull()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed request fields snippet`() {
            val snippet = Snippets.requestFields(relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a request fields snippet with attributes`() {
            val snippet = Snippets.requestFields(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request fields snippet with a subsection extractor`() {
            val beneathUser = PayloadDocumentation.beneathPath("user")
            val snippet = Snippets.requestFields(subsectionExtractor = beneathUser) {
            }

            assertThat(snippet.payloadSubsectionExtractor).isSameAs(beneathUser)
        }

        @Test
        fun `should create a request fields snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.field("firstName", "the user first name")
            val snippet = Snippets.requestFields {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors.first()).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a request fields snippet with parameters defined inline`() {
            val snippet = Snippets.requestFields {
                add(
                    "firstName",
                    "the user first name",
                    optional = true,
                    type = JsonFieldType.STRING,
                    attributes = mapOf("a" to "b", "c" to null)
                )
                add("dummy", ignored = true)
            }

            with(snippet.descriptors[0]) {
                assertThat(path).isEqualTo("firstName")
                assertThat(description).isEqualTo("the user first name")
                assertThat(isOptional).isTrue()
                assertThat(type).isEqualTo(JsonFieldType.STRING)
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors[1].isIgnored).isTrue()
        }

        @Test
        fun `should create a request fields snippet with subsections defined inline`() {
            val snippet = Snippets.requestFields {
                addSubsection(
                    "address",
                    "the user address",
                    optional = true,
                    type = JsonFieldType.OBJECT,
                    attributes = mapOf("a" to "b", "c" to null)
                )
                addSubsection("dummy", ignored = true
                )
            }

            with(snippet.descriptors[0]) {
                assertThat(javaClass).isEqualTo(SubsectionDescriptor::class.java)
                assertThat(path).isEqualTo("address")
                assertThat(description).isEqualTo("the user address")
                assertThat(isOptional).isTrue()
                assertThat(type).isEqualTo(JsonFieldType.OBJECT)
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            with(snippet.descriptors[1]) {
                assertThat(javaClass).isEqualTo(SubsectionDescriptor::class.java)
                assertThat(isIgnored).isTrue()
            }
        }

        @Test
        fun `should create a request fields snippet with a fields under a prefix`() {
            val snippet = Snippets.requestFields {
                withPrefix("address.") {
                    add("street", "the street")
                    add("country", "the iso country code")
                }
            }

            assertThat(snippet.descriptors.map { it.path }).containsExactly("address.street", "address.country")
        }
    }

    @Nested
    inner class ResponseFields {
        @Test
        fun `should create a non-relaxed, no-attribute, no subsection-extractor response fields snippet by default`() {
            val snippet = Snippets.responseFields {
            }

            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.payloadSubsectionExtractor).isNull()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed response fields snippet`() {
            val snippet = Snippets.responseFields(relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a response fields snippet with attributes`() {
            val snippet = Snippets.responseFields(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a response fields snippet with a subsection extractor`() {
            val beneathUser = PayloadDocumentation.beneathPath("user")
            val snippet = Snippets.responseFields(subsectionExtractor = beneathUser) {
            }

            assertThat(snippet.payloadSubsectionExtractor).isSameAs(beneathUser)
        }

        @Test
        fun `should create a response fields snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.field("firstName", "the user first name")
            val snippet = Snippets.responseFields {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors.first()).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a response fields snippet with parameters defined inline`() {
            val snippet = Snippets.responseFields {
                add(
                    "firstName",
                    "the user first name",
                    optional = true,
                    type = JsonFieldType.STRING,
                    attributes = mapOf("a" to "b", "c" to null)
                )
                add("dummy", ignored = true)
            }

            with(snippet.descriptors[0]) {
                assertThat(path).isEqualTo("firstName")
                assertThat(description).isEqualTo("the user first name")
                assertThat(isOptional).isTrue()
                assertThat(type).isEqualTo(JsonFieldType.STRING)
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors[1].isIgnored).isTrue()
        }

        @Test
        fun `should create a response fields snippet with a subsection`() {
            val snippet = Snippets.responseFields {
                addSubsection(
                    "address",
                    "the user address",
                    optional = true,
                    type = JsonFieldType.OBJECT,
                    attributes = mapOf("a" to "b", "c" to null)
                )
            }

            with(snippet.descriptors[0]) {
                assertThat(javaClass).isEqualTo(SubsectionDescriptor::class.java)
                assertThat(path).isEqualTo("address")
                assertThat(description).isEqualTo("the user address")
                assertThat(isOptional).isTrue()
                assertThat(type).isEqualTo(JsonFieldType.OBJECT)
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
        }

        @Test
        fun `should create a response fields snippet with a fields under a prefix`() {
            val snippet = Snippets.responseFields {
                withPrefix("address.") {
                    add("street", "the street")
                    add("country", "the iso country code")
                }
            }

            assertThat(snippet.descriptors.map { it.path }).containsExactly("address.street", "address.country")
        }
    }

    @Nested
    inner class RequestHeaders {
        @Test
        fun `should create a no-attribute request headers snippet by default`() {
            val snippet = Snippets.requestHeaders {
            }

            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a request headers snippet with attributes`() {
            val snippet = Snippets.requestHeaders(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request headers snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.header(HttpHeaders.AUTHORIZATION, "the basic auth credentials")
            val snippet = Snippets.requestHeaders {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors[0]).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a request headers snippet with parameters defined inline`() {
            val snippet = Snippets.requestHeaders {
                add(
                    HttpHeaders.AUTHORIZATION,
                    "the basic auth credentials",
                    optional = true,
                    attributes = mapOf("a" to "b", "c" to null)
                )
                add(HttpHeaders.CONTENT_TYPE, "the image content type")
            }

            assertThat(snippet.descriptors).hasSize(2)
            with(snippet.descriptors[0]) {
                assertThat(name).isEqualTo(HttpHeaders.AUTHORIZATION)
                assertThat(description).isEqualTo("the basic auth credentials")
                assertThat(isOptional).isTrue()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
        }
    }

    @Nested
    inner class ResponseHeaders {
        @Test
        fun `should create a no-attribute response headers snippet by default`() {
            val snippet = Snippets.responseHeaders {
            }

            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a response headers snippet with attributes`() {
            val snippet = Snippets.responseHeaders(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a response headers snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.header(HttpHeaders.CONTENT_TYPE, "the content type of the image")
            val snippet = Snippets.responseHeaders {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors[0]).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a response headers snippet with parameters defined inline`() {
            val snippet = Snippets.responseHeaders {
                add(
                    HttpHeaders.CONTENT_TYPE,
                    "the content type of the image",
                    optional = true,
                    attributes = mapOf("a" to "b", "c" to null)
                )
                add(HttpHeaders.CONTENT_LENGTH, "the size of the image in bytes")
            }

            assertThat(snippet.descriptors).hasSize(2)
            with(snippet.descriptors[0]) {
                assertThat(name).isEqualTo(HttpHeaders.CONTENT_TYPE)
                assertThat(description).isEqualTo("the content type of the image")
                assertThat(isOptional).isTrue()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
        }
    }

    @Nested
    inner class RequestParts {
        @Test
        fun `should create a non-relaxed, no-attribute request parts snippet by default`() {
            val snippet = Snippets.requestParts {
            }

            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed request parts snippet`() {
            val snippet = Snippets.requestParts(relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a request parts snippet with attributes`() {
            val snippet = Snippets.requestParts(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request parts snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.requestPart("file", "the uploaded image")
            val snippet = Snippets.requestParts {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors["file"]).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a request parts snippet with parameters defined inline`() {
            val snippet = Snippets.requestParts {
                add("file", "the uploaded image", optional = true, attributes = mapOf("a" to "b", "c" to null))
                add("dummy", ignored = true)
            }

            with(snippet.descriptors["file"]!!) {
                assertThat(name).isEqualTo("file")
                assertThat(description).isEqualTo("the uploaded image")
                assertThat(isOptional).isTrue()
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors["dummy"]?.isIgnored).isTrue()
        }
    }

    @Nested
    inner class RequestPartFields {
        @Test
        fun `should create a non-relaxed, no-attribute, no subsection-extractor request part fields snippet by default`() {
            val snippet = Snippets.requestPartFields("form") {
            }

            assertThat(snippet.partName).isEqualTo("form")
            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.payloadSubsectionExtractor).isNull()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed request part fields snippet`() {
            val snippet = Snippets.requestPartFields("form", relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a request part fields snippet with attributes`() {
            val snippet = Snippets.requestPartFields("form", attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request part fields snippet with a subsection extractor`() {
            val beneathUser = PayloadDocumentation.beneathPath("user")
            val snippet = Snippets.requestPartFields("form", subsectionExtractor = beneathUser) {
            }

            assertThat(snippet.payloadSubsectionExtractor).isSameAs(beneathUser)
        }

        @Test
        fun `should create a request part fields snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.field("firstName", "the user first name")
            val snippet = Snippets.requestPartFields("form") {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors.first()).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a request part fields snippet with parameters defined inline`() {
            val snippet = Snippets.requestPartFields("form") {
                add(
                    "firstName",
                    "the user first name",
                    optional = true,
                    type = JsonFieldType.STRING,
                    attributes = mapOf("a" to "b", "c" to null)
                )
                add("dummy", ignored = true)
            }

            with(snippet.descriptors[0]) {
                assertThat(path).isEqualTo("firstName")
                assertThat(description).isEqualTo("the user first name")
                assertThat(isOptional).isTrue()
                assertThat(type).isEqualTo(JsonFieldType.STRING)
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors[1].isIgnored).isTrue()
        }

        @Test
        fun `should create a request part fields snippet with a subsection`() {
            val snippet = Snippets.requestPartFields("form") {
                addSubsection(
                    "address",
                    "the user address",
                    optional = true,
                    type = JsonFieldType.OBJECT,
                    attributes = mapOf("a" to "b", "c" to null)
                )
            }

            with(snippet.descriptors[0]) {
                assertThat(javaClass).isEqualTo(SubsectionDescriptor::class.java)
                assertThat(path).isEqualTo("address")
                assertThat(description).isEqualTo("the user address")
                assertThat(isOptional).isTrue()
                assertThat(type).isEqualTo(JsonFieldType.OBJECT)
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
        }

        @Test
        fun `should create a request part fields snippet with a fields under a prefix`() {
            val snippet = Snippets.requestPartFields("form") {
                withPrefix("address.") {
                    add("street", "the street")
                    add("country", "the iso country code")
                }
            }

            assertThat(snippet.descriptors.map { it.path }).containsExactly("address.street", "address.country")
        }
    }

    @Nested
    inner class RequestBody {
        @Test
        fun `should create a no-attribute, no subsection extractor request body snippet by default`() {
            val snippet = Snippets.requestBody()

            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.payloadSubsectionExtractor).isNull()
        }

        @Test
        fun `should create a request body snippet with attributes`() {
            val snippet = Snippets.requestBody(attributes = mapOf("a" to "b", "c" to null))

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request body snippet with a subsection extractor`() {
            val beneathUser = PayloadDocumentation.beneathPath("user")
            val snippet = Snippets.requestBody(subsectionExtractor = beneathUser)

            assertThat(snippet.payloadSubsectionExtractor).isSameAs(beneathUser)
        }
    }

    @Nested
    inner class ResponseBody {
        @Test
        fun `should create a no-attribute, no subsection extractor response body snippet by default`() {
            val snippet = Snippets.responseBody()

            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.payloadSubsectionExtractor).isNull()
        }

        @Test
        fun `should create a response body snippet with attributes`() {
            val snippet = Snippets.responseBody(attributes = mapOf("a" to "b", "c" to null))

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a response body snippet with a subsection extractor`() {
            val beneathUser = PayloadDocumentation.beneathPath("user")
            val snippet = Snippets.responseBody(subsectionExtractor = beneathUser)

            assertThat(snippet.payloadSubsectionExtractor).isSameAs(beneathUser)
        }
    }

    @Nested
    inner class RequestPartBody {
        @Test
        fun `should create a no-attribute, no subsection extractor request part body snippet by default`() {
            val snippet = Snippets.requestPartBody("form")

            assertThat(snippet.partName).isEqualTo("form")
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.payloadSubsectionExtractor).isNull()
        }

        @Test
        fun `should create a request part body snippet with attributes`() {
            val snippet = Snippets.requestPartBody("form", attributes = mapOf("a" to "b", "c" to null))

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a request part body snippet with a subsection extractor`() {
            val beneathUser = PayloadDocumentation.beneathPath("user")
            val snippet = Snippets.requestPartBody("form", subsectionExtractor = beneathUser)

            assertThat(snippet.payloadSubsectionExtractor).isSameAs(beneathUser)
        }
    }

    @Nested
    inner class Links {
        @Test
        fun `should create a non-relaxed, no-attribute links snippet with default extractors by default`() {
            val snippet = Snippets.links {
            }

            val defaultExtractor = HypermediaDocumentation.links().linkExtractor

            assertThat(snippet.relaxed).isFalse()
            assertThat(snippet.linkExtractor).hasSameClassAs(defaultExtractor)
            assertThat(snippet.attributeMap).isEmpty()
            assertThat(snippet.descriptors).isEmpty()
        }

        @Test
        fun `should create a relaxed links snippet`() {
            val snippet = Snippets.links(relaxed = true) {
            }

            assertThat(snippet.relaxed).isTrue()
        }

        @Test
        fun `should create a links snippet with a link extractor`() {
            val extractor = HypermediaDocumentation.halLinks()
            val snippet = Snippets.links(linkExtractor = extractor) {
            }

            assertThat(snippet.linkExtractor).isSameAs(extractor)
        }

        @Test
        fun `should create a links snippet with attributes`() {
            val snippet = Snippets.links(attributes = mapOf("a" to "b", "c" to null)) {
            }

            assertThat(snippet.attributeMap).containsOnly(entry("a", "b"), entry("c", null))
        }

        @Test
        fun `should create a links snippet with a reusable descriptor`() {
            val reusableDescriptor = Descriptors.link("next", "the next page URL")
            val snippet = Snippets.links {
                add(reusableDescriptor)
            }

            assertThat(snippet.descriptors["next"]).isSameAs(reusableDescriptor)
        }

        @Test
        fun `should create a links snippet with parameters defined inline`() {
            val snippet = Snippets.links {
                add("next", "the next page URL", optional = true, attributes = mapOf("a" to "b", "c" to null))
                add("dummy", ignored = true)
            }

            with(snippet.descriptors["next"]!!) {
                assertThat(rel).isEqualTo("next")
                assertThat(description).isEqualTo("the next page URL")
                assertThat(isOptional).isTrue()
                assertThat(isIgnored).isFalse()
                assertThat(attributes).containsOnly(entry("a", "b"), entry("c", null))
            }
            assertThat(snippet.descriptors["dummy"]?.isIgnored).isTrue()
        }
    }
}
