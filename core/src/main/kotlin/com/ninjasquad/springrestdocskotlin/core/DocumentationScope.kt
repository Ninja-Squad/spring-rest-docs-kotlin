package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.hypermedia.LinkExtractor
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.payload.PayloadSubsectionExtractor
import org.springframework.restdocs.snippet.Snippet

/**
 * Receiver of the extension functions allowing to add various snippets to the documentation of an API call to a
 * RESTful resource.
 * These extension functions are specific to the test framework (MockMvc, WebTestClient, RestAssured) chosen
 * to generate the documentation.
 */
@RestDocumentationDslMarker
interface DocumentationScope {

    /**
     * The identifier of the API call documented
     */
    val identifier: String

    /**
     * Allows setting (and getting) a [request preprocessor][OperationRequestPreprocessor] used to modify the request
     * prior to it being documented.
     */
    var requestPreprocessor: OperationRequestPreprocessor?

    /**
     * Allows setting (and getting) a [response preprocessor][OperationResponsePreprocessor] used to modify the request
     * prior to it being documented.
     */
    var responsePreprocessor: OperationResponsePreprocessor?

    /**
     * The list of snippets that have been added to the documentation
     */
    val snippets: List<Snippet>

    /**
     * Adds the given snippet to the documentation.
     * @param snippet the snippet to add
     */
    fun snippet(snippet: Snippet)

    /**
     * Creates a new [org.springframework.restdocs.request.PathParametersSnippet] and adds it to the documentation.
     * @param relaxed if true, undocumented parameters present in the request path will be ignored. If false,
     * a failure will occur if undocumented parameters are present in the request path.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [ParametersScope] as receiver, allowing to add parameter descriptors to
     * the snippet
     */
    fun pathParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.request.RequestParametersSnippet] and adds it to the documentation.
     * @param relaxed if true, undocumented parameters present in the request parameters will be ignored. If false,
     * a failure will occur if undocumented parameters are present in the request parameters.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [ParametersScope] as receiver, allowing to add parameter descriptors to
     * the snippet
     */
    fun requestParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.payload.RequestFieldsSnippet] and adds it to the documentation.
     * @param relaxed if true, undocumented fields present in the request payload will be ignored. If false,
     * a failure will occur if undocumented fields are present in the request payload.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors to
     * the snippet
     */
    fun requestFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.payload.ResponseFieldsSnippet] and adds it to the documentation.
     * @param relaxed if true, undocumented fields present in the response payload will be ignored. If false,
     * a failure will occur if undocumented fields are present in the response payload.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors to
     * the snippet
     */
    fun responseFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.headers.RequestHeadersSnippet] and adds it to the documentation.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [HeadersScope] as receiver, allowing to add header descriptors to
     * the snippet
     */
    fun requestHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.headers.ResponseHeadersSnippet] and adds it to the documentation.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [HeadersScope] as receiver, allowing to add header descriptors to
     * the snippet
     */
    fun responseHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.request.RequestPartsSnippet] and adds it to the documentation.
     * @param relaxed if true, undocumented parts present in the request will be ignored. If false,
     * a failure will occur if undocumented parts are present in the request.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [RequestPartsScope] as receiver, allowing to add request part descriptors to
     * the snippet
     */
    fun requestParts(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: RequestPartsScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.payload.RequestPartFieldsSnippet] and adds it to the documentation.
     * @param relaxed if true, undocumented fields present in the request part payload will be ignored. If false,
     * a failure will occur if undocumented fields are present in the request part payload.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors to
     * the snippet
     */
    fun requestPartFields(
        part: String,
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    )

    /**
     * Creates a new [org.springframework.restdocs.payload.RequestBodySnippet] and adds it to the documentation.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     */
    fun requestBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Creates a new [org.springframework.restdocs.payload.ResponseBodySnippet] and adds it to the documentation.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     */
    fun responseBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Creates a new [org.springframework.restdocs.payload.RequestPartBodySnippet] and adds it to the documentation.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     */
    fun requestPartBody(
        part: String,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Creates a new [LinksSnippet] and adds it to the documentation .
     * @param relaxed if true, undocumented links present in the response will be ignored. If false,
     * a failure will occur if undocumented links are present in the response.
     * @param linkExtractor used to extract the links from the response. If null, links will be extracted from the
     * response automatically based on its content type
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [LinksScope] as receiver, allowing to add link descriptors to the snippet
     */
    fun links(
        relaxed: Boolean = false,
        linkExtractor: LinkExtractor? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: LinksScope.() -> Unit
    )
}
