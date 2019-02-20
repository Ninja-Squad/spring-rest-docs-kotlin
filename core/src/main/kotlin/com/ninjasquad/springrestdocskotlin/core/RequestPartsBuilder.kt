package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.request.RequestPartsSnippet

/**
 * Implementation of [RequestPartsScope], allowing to build instances of [RequestPartsSnippet]
 */
internal class RequestPartsBuilder : RequestPartsScope {

    private val parts = mutableListOf<RequestPartDescriptor>()

    override fun add(
        name: String,
        description: String?,
        optional: Boolean,
        ignored: Boolean,
        attributes: Map<String, Any?>
    ) = add(
        Descriptors.requestPart(
            name = name,
            description = description,
            optional = optional,
            ignored = ignored,
            attributes = attributes
        )
    )

    override fun add(requestPartDescriptor: RequestPartDescriptor) {
        parts.add(requestPartDescriptor)
    }

    fun build(relaxed: Boolean, attributes: Map<String, Any?>): RequestPartsSnippet =
        if (relaxed) {
            RequestDocumentation.relaxedRequestParts(attributes, parts)
        } else {
            RequestDocumentation.requestParts(attributes, parts)
        }
}
