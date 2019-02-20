package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.headers.ResponseHeadersSnippet

/**
 * Implementation of [HeadersScope], allowing to build instances of [RequestHeadersSnippet] and
 * [ResponseHeadersSnippet]
 */
internal class HeadersBuilder : HeadersScope {
    private val headers = mutableListOf<HeaderDescriptor>()

    override fun add(
        name: String,
        description: String,
        optional: Boolean, attributes: Map<String, Any?>
    ) = add(
        Descriptors.header(
            name = name,
            description = description,
            optional = optional,
            attributes = attributes
        )
    )

    override fun add(headerDescriptor: HeaderDescriptor) {
        headers.add(headerDescriptor)
    }

    fun buildRequestHeaders(attributes: Map<String, Any?>): RequestHeadersSnippet =
        HeaderDocumentation.requestHeaders(attributes, headers)

    fun buildResponseHeaders(attributes: Map<String, Any?>): ResponseHeadersSnippet =
        HeaderDocumentation.responseHeaders(attributes, headers)
}
