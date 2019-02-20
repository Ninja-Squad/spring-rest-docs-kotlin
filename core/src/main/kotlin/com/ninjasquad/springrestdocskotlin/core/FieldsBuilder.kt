package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.payload.*

/**
 * Implementation of [FieldsScope], allowing to build instances of [RequestFieldsSnippet], [ResponseFieldsSnippet]
 * and [RequestPartFieldsSnippet].
 */
internal class FieldsBuilder : FieldsScope {
    private val fields = mutableListOf<FieldDescriptor>()

    override fun add(
        path: String,
        description: String?,
        optional: Boolean,
        ignored: Boolean,
        type: Any?,
        attributes: Map<String, Any?>
    ) = add(
        Descriptors.field(
            path = path,
            description = description,
            optional = optional,
            ignored = ignored,
            type = type,
            attributes = attributes
        )
    )

    override fun addSubsection(
        path: String,
        description: String?,
        optional: Boolean,
        ignored: Boolean,
        type: Any?,
        attributes: Map<String, Any?>
    ) {
        add(
            Descriptors.subsection(
                path = path,
                description = description,
                optional = optional,
                ignored = ignored,
                type = type,
                attributes = attributes
            )
        )
    }

    override fun add(fieldDescriptor: FieldDescriptor) {
        fields.add(fieldDescriptor)
    }

    override fun withPrefix(prefix: String, configure: FieldsScope.() -> Unit) {
        val fields = FieldsBuilder().apply(configure).fields
        PayloadDocumentation.applyPathPrefix(prefix, fields).forEach(::add)
    }

    fun buildRequestFields(
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ): RequestFieldsSnippet =
        if (relaxed) {
            PayloadDocumentation.relaxedRequestFields(
                subsectionExtractor,
                attributes,
                fields
            )
        } else {
            PayloadDocumentation.requestFields(
                subsectionExtractor,
                attributes,
                fields
            )
        }

    fun buildResponseFields(
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ): ResponseFieldsSnippet =
        if (relaxed) {
            PayloadDocumentation.relaxedResponseFields(
                subsectionExtractor,
                attributes,
                fields
            )
        } else {
            PayloadDocumentation.responseFields(
                subsectionExtractor,
                attributes,
                fields
            )
        }

    fun buildRequestPartFields(
        part: String,
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ): RequestPartFieldsSnippet =
        if (relaxed) {
            PayloadDocumentation.relaxedRequestPartFields(
                part,
                subsectionExtractor,
                attributes,
                fields
            )
        } else {
            PayloadDocumentation.requestPartFields(
                part,
                subsectionExtractor,
                attributes,
                fields
            )
        }
}
