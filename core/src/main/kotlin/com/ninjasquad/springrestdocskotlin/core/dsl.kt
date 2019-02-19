package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.headers.ResponseHeadersSnippet
import org.springframework.restdocs.hypermedia.HypermediaDocumentation
import org.springframework.restdocs.hypermedia.LinkDescriptor
import org.springframework.restdocs.hypermedia.LinkExtractor
import org.springframework.restdocs.hypermedia.LinksSnippet
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.request.*
import org.springframework.restdocs.snippet.Attributes
import org.springframework.restdocs.snippet.Snippet

object Snippets {
    fun pathParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    ): PathParametersSnippet = ParametersBuilder().apply(configure).buildPathParameters(relaxed, attributes)

    fun requestParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    ): RequestParametersSnippet = ParametersBuilder().apply(configure).buildRequestParameters(relaxed, attributes)

    fun requestParts(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: RequestPartsScope.() -> Unit
    ): RequestPartsSnippet = RequestPartsBuilder().apply(configure).build(relaxed, attributes)

    fun requestFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    ): RequestFieldsSnippet = FieldsBuilder().apply(configure).buildRequestFields(relaxed, subsectionExtractor, attributes)

    fun responseFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    ): ResponseFieldsSnippet = FieldsBuilder().apply(configure).buildResponseFields(relaxed, subsectionExtractor, attributes)

    fun requestPartFields(
        part: String,
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    ): RequestPartFieldsSnippet = FieldsBuilder()
        .apply(configure)
        .buildRequestPartFields(part, relaxed, subsectionExtractor, attributes)

    fun requestBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    ) : RequestBodySnippet = PayloadDocumentation.requestBody(subsectionExtractor, attributes)

    fun responseBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    ) : ResponseBodySnippet = PayloadDocumentation.responseBody(subsectionExtractor, attributes)

    fun requestPartBody(
        part: String,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    ) : RequestPartBodySnippet = PayloadDocumentation.requestPartBody(part, subsectionExtractor, attributes)

    fun requestHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    ): RequestHeadersSnippet = HeadersBuilder().apply(configure).buildRequestHeaders(attributes)

    fun responseHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    ): ResponseHeadersSnippet = HeadersBuilder().apply(configure).buildResponseHeaders(attributes)

    fun links(
        relaxed: Boolean = false,
        linkExtractor: LinkExtractor? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: LinksScope.() -> Unit
    ): LinksSnippet = LinksBuilder().apply(configure).build(relaxed, linkExtractor, attributes)
}

object Descriptors {
    fun parameter(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): ParameterDescriptor = RequestDocumentation.parameterWithName(name)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .attributes(*attributes.toAttributes())

    fun requestPart(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): RequestPartDescriptor = RequestDocumentation.partWithName(name)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .attributes(*attributes.toAttributes())

    fun field(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    ): FieldDescriptor = PayloadDocumentation.fieldWithPath(path)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .type(type)
        .attributes(*attributes.toAttributes())

    fun subsection(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    ): SubsectionDescriptor = PayloadDocumentation.subsectionWithPath(path)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .type(type)
        .attributes(*attributes.toAttributes()) as SubsectionDescriptor

    fun header(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): HeaderDescriptor = HeaderDocumentation.headerWithName(name)
        .description(description)
        .apply {
            if (optional) optional()
        }
        .attributes(*attributes.toAttributes())

    fun link(
        rel: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): LinkDescriptor = HypermediaDocumentation.linkWithRel(rel)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .attributes(*attributes.toAttributes())
}

interface DocumentationScope {
    val identifier: String
    var requestPreprocessor: OperationRequestPreprocessor?
    var responsePreprocessor: OperationResponsePreprocessor?
    val snippets: List<Snippet>

    fun snippet(snippet: Snippet)

    fun pathParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    )

    fun requestParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    )

    fun requestFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    )

    fun responseFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    )

    fun requestParts(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: RequestPartsScope.() -> Unit
    )

    fun requestPartFields(
        part: String,
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    )

    fun requestBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun responseBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun requestPartBody(
        part: String,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun requestHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    )

    fun responseHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    )

    fun links(
        relaxed: Boolean = false,
        linkExtractor: LinkExtractor? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: LinksScope.() -> Unit
    )
}

fun documentationScope(identifier: String): DocumentationScope
    = DocumentationBuilder(identifier)

private class DocumentationBuilder(override val identifier: String) : DocumentationScope {

    override var requestPreprocessor: OperationRequestPreprocessor? = null

    override var responsePreprocessor: OperationResponsePreprocessor? = null

    private val mutableSnippets = mutableListOf<Snippet>()
    override val snippets: List<Snippet>
        get() = mutableSnippets

    override fun snippet(snippet: Snippet) {
        mutableSnippets.add(snippet)
    }

    override fun pathParameters(
        relaxed: Boolean,
        attributes: Map<String, Any?>,
        configure: ParametersScope.() -> Unit
    ) = snippet(Snippets.pathParameters(relaxed, attributes, configure))

    override fun requestParameters(
        relaxed: Boolean,
        attributes: Map<String, Any?>,
        configure: ParametersScope.() -> Unit
    ) = snippet(Snippets.requestParameters(relaxed, attributes, configure))

    override fun requestFields(
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>,
        configure: FieldsScope.() -> Unit
    ) = snippet(Snippets.requestFields(relaxed, subsectionExtractor, attributes, configure))

    override fun responseFields(
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>,
        configure: FieldsScope.() -> Unit
    ) = snippet(Snippets.responseFields(relaxed, subsectionExtractor, attributes, configure))

    override fun requestParts(
        relaxed: Boolean,
        attributes: Map<String, Any?>,
        configure: RequestPartsScope.() -> Unit
    ) = snippet(Snippets.requestParts(relaxed, attributes, configure))

    override fun requestPartFields(
        part: String,
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>,
        configure: FieldsScope.() -> Unit
    ) = snippet(Snippets.requestPartFields(part, relaxed, subsectionExtractor, attributes, configure))

    override fun requestBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ) = snippet(Snippets.requestBody(subsectionExtractor, attributes))

    override fun responseBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ) = snippet(Snippets.responseBody(subsectionExtractor, attributes))

    override fun requestPartBody(
        part: String,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ) = snippet(Snippets.requestPartBody(part, subsectionExtractor, attributes))

    override fun requestHeaders(
        attributes: Map<String, Any?>,
        configure: HeadersScope.() -> Unit
    ) = snippet(Snippets.requestHeaders(attributes, configure))

    override fun responseHeaders(
        attributes: Map<String, Any?>,
        configure: HeadersScope.() -> Unit
    ) = snippet(Snippets.responseHeaders(attributes, configure))

    override fun links(
        relaxed: Boolean,
        linkExtractor: LinkExtractor?,
        attributes: Map<String, Any?>,
        configure: LinksScope.() -> Unit
    ) = snippet(Snippets.links(relaxed, linkExtractor, attributes, configure))
}

interface ParametersScope {
    fun add(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun add(parameterDescriptor: ParameterDescriptor)
}

private class ParametersBuilder : ParametersScope {
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

interface RequestPartsScope {
    fun add(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun add(requestPartDescriptor: RequestPartDescriptor)
}

private class RequestPartsBuilder : RequestPartsScope {

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

interface FieldsScope {
    fun add(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun addSubsection(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun add(fieldDescriptor: FieldDescriptor)

    fun withPrefix(prefix: String, configure: FieldsScope.() -> Unit)
}

private class FieldsBuilder : FieldsScope {
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
            PayloadDocumentation.relaxedRequestFields(subsectionExtractor, attributes, fields)
        } else {
            PayloadDocumentation.requestFields(subsectionExtractor, attributes, fields)
        }

    fun buildResponseFields(
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ): ResponseFieldsSnippet =
        if (relaxed) {
            PayloadDocumentation.relaxedResponseFields(subsectionExtractor, attributes, fields)
        } else {
            PayloadDocumentation.responseFields(subsectionExtractor, attributes, fields)
        }

    fun buildRequestPartFields(
        part: String,
        relaxed: Boolean,
        subsectionExtractor: PayloadSubsectionExtractor<*>?,
        attributes: Map<String, Any?>
    ): RequestPartFieldsSnippet =
        if (relaxed) {
            PayloadDocumentation.relaxedRequestPartFields(part, subsectionExtractor, attributes, fields)
        } else {
            PayloadDocumentation.requestPartFields(part, subsectionExtractor, attributes, fields)
        }
}

interface HeadersScope {
    fun add(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun add(headerDescriptor: HeaderDescriptor)
}

private class HeadersBuilder : HeadersScope {
    private val headers = mutableListOf<HeaderDescriptor>()

    override fun add(
        name: String,
        description: String?,
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

interface LinksScope {
    fun add(
        rel: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    fun add(linkDescriptor: LinkDescriptor)
}

private class LinksBuilder : LinksScope {
    private val links = mutableListOf<LinkDescriptor>()

    override fun add(
        rel: String,
        description: String?,
        optional: Boolean,
        ignored: Boolean,
        attributes: Map<String, Any?>
    ) = add(Descriptors.link(rel, description, optional, ignored, attributes))

    override fun add(linkDescriptor: LinkDescriptor) {
        links.add(linkDescriptor)
    }

    fun build(
        relaxed: Boolean,
        linkExtractor: LinkExtractor? = null,
        attributes: Map<String, Any?>
    ): LinksSnippet =
        if (relaxed) {
            linkExtractor
                ?.let { HypermediaDocumentation.relaxedLinks(linkExtractor, attributes, links) }
                ?: HypermediaDocumentation.relaxedLinks(attributes, links)
        } else {
            linkExtractor
                ?.let { HypermediaDocumentation.links(linkExtractor, attributes, links) }
                ?: HypermediaDocumentation.links(attributes, links)
        }
}

private fun Map<String, Any?>.toAttributes() = map { Attributes.key(it.key).value(it.value) }.toTypedArray()
