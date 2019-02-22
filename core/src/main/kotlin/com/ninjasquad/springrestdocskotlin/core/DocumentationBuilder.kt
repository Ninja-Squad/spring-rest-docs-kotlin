package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.hypermedia.LinkExtractor
import org.springframework.restdocs.operation.OperationRequest
import org.springframework.restdocs.operation.OperationResponse
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.payload.PayloadSubsectionExtractor
import org.springframework.restdocs.snippet.Snippet

/**
 * Implementation of [DocumentationScope]
 */
internal class DocumentationBuilder(override val identifier: String) : DocumentationScope {

    override var requestPreprocessor: OperationRequestPreprocessor = identityRequestPreprocessor

    override var responsePreprocessor: OperationResponsePreprocessor = identityResponsePreprocessor

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

    override fun preprocessRequest(configure: RequestPreprocessorScope.() -> Unit) {
        requestPreprocessor = RequestPreprocessorBuilder().apply(configure).build()
    }

    override fun preprocessResponse(configure: ResponsePreprocessorScope.() -> Unit) {
        responsePreprocessor = ResponsePreprocessorBuilder().apply(configure).build()
    }
}

/**
 * Creates a new instance of [DocumentationScope], allowing to create and configure the snippets documenting a
 * RESTful resource.
 *
 * This function is meant to be called by the various testing frameworks (MockMVC, WebTestClient, RestAssured),
 * each providing a specific function to generate the documentation.
 *
 * @param identifier the identifier of the API call being documented by the created documentation scope
 * @return the created documentation scope
 */
fun documentationScope(identifier: String): DocumentationScope
    = DocumentationBuilder(identifier)

private object identityRequestPreprocessor: OperationRequestPreprocessor {
    override fun preprocess(request: OperationRequest) = request
}

private object identityResponsePreprocessor: OperationResponsePreprocessor {
    override fun preprocess(response: OperationResponse) = response
}
