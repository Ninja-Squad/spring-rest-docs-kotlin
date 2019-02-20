package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestParametersSnippet

/**
 * Implementation of [ParametersScope], allowing to build instances of [PathParametersSnippet] and
 * [RequestParametersSnippet]
 */
internal class ParametersBuilder : ParametersScope {
    private val parameters = mutableListOf<ParameterDescriptor>()

    override fun add(
        name: String,
        description: String?,
        optional: Boolean,
        ignored: Boolean,
        attributes: Map<String, Any?>
    ) = add(
        Descriptors.parameter(
            name = name,
            description = description,
            optional = optional,
            ignored = ignored,
            attributes = attributes
        )
    )

    override fun add(parameterDescriptor: ParameterDescriptor) {
        parameters.add(parameterDescriptor)
    }

    fun buildPathParameters(relaxed: Boolean, attributes: Map<String, Any?>): PathParametersSnippet =
        if (relaxed) {
            RequestDocumentation.relaxedPathParameters(attributes, parameters)
        } else {
            RequestDocumentation.pathParameters(attributes, parameters)
        }

    fun buildRequestParameters(relaxed: Boolean, attributes: Map<String, Any?>): RequestParametersSnippet =
        if (relaxed) {
            RequestDocumentation.relaxedRequestParameters(attributes, parameters)
        } else {
            RequestDocumentation.requestParameters(attributes, parameters)
        }
}

