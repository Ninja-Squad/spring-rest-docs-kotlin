package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.headers.RequestHeadersSnippet
import org.springframework.restdocs.headers.ResponseHeadersSnippet
import org.springframework.restdocs.hypermedia.LinkExtractor
import org.springframework.restdocs.hypermedia.LinksSnippet
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestParametersSnippet
import org.springframework.restdocs.request.RequestPartsSnippet

/**
 * Object serving as a factory for reusable snippets, of all types.
 *
 * Most methods of this object take an extension function as last argument, used to configure, i.e. add descriptors to,
 * the created snippet.
 *
 * The other arguments are usually optional, and intended to be used as named arguments, to improve readability and to
 * promote forward compatibility:
 * ```
 * // DON'T:
 * val userId = Snippets.requestParameters(true) {
 *     add("query", "the full text query")
 * }
 *
 * // DO:
 * val userId = Snippets.requestParameters(relaxed = true) {
 *     add("query", "the full text query")
 * }
 * ```
 *
 * @author JB Nizet
 */
object Snippets {

    /**
     * Creates a new reusable [PathParametersSnippet] describing the path parameters supported by a RESTful resource.
     * @param relaxed if true, undocumented parameters present in the request path will be ignored. If false,
     * a failure will occur if undocumented parameters are present in the request path.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [ParametersScope] as receiver, allowing to add parameter descriptors to
     * the snippet
     * @return the created snippet
     */
    fun pathParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    ): PathParametersSnippet = ParametersBuilder().apply(configure).buildPathParameters(relaxed, attributes)

    /**
     * Creates a new reusable [RequestParametersSnippet] describing the request parameters supported by a RESTful
     * resource.
     * @param relaxed if true, undocumented parameters present in the request parameters will be ignored. If false,
     * a failure will occur if undocumented parameters are present in the request parameters.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [ParametersScope] as receiver, allowing to add parameter descriptors to
     * the snippet
     * @return the created snippet
     */
    fun requestParameters(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: ParametersScope.() -> Unit
    ): RequestParametersSnippet = ParametersBuilder().apply(configure).buildRequestParameters(relaxed, attributes)

    /**
     * Creates a new reusable [RequestFieldsSnippet] describing the fields in a request payload.
     * @param relaxed if true, undocumented fields present in the request payload will be ignored. If false,
     * a failure will occur if undocumented fields are present in the request payload.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors to
     * the snippet
     * @return the created snippet
     */
    fun requestFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    ): RequestFieldsSnippet = FieldsBuilder().apply(configure).buildRequestFields(relaxed, subsectionExtractor, attributes)

    /**
     * Creates a new reusable [ResponseFieldsSnippet] describing the fields in a response payload.
     * @param relaxed if true, undocumented fields present in the response payload will be ignored. If false,
     * a failure will occur if undocumented fields are present in the response payload.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors to
     * the snippet
     * @return the created snippet
     */
    fun responseFields(
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    ): ResponseFieldsSnippet = FieldsBuilder().apply(configure).buildResponseFields(relaxed, subsectionExtractor, attributes)

    /**
     * Creates a new reusable [RequestHeadersSnippet] describing the headers in a request.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [HeadersScope] as receiver, allowing to add header descriptors to
     * the snippet
     * @return the created snippet
     */
    fun requestHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    ): RequestHeadersSnippet = HeadersBuilder().apply(configure).buildRequestHeaders(attributes)

    /**
     * Creates a new reusable [ResponseHeadersSnippet] describing the headers in a request.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [HeadersScope] as receiver, allowing to add header descriptors to
     * the snippet
     * @return the created snippet
     */
    fun responseHeaders(
        attributes: Map<String, Any?> = emptyMap(),
        configure: HeadersScope.() -> Unit
    ): ResponseHeadersSnippet = HeadersBuilder().apply(configure).buildResponseHeaders(attributes)

    /**
     * Creates a new reusable [RequestPartsSnippet] describing the parts of a multipart request.
     * @param relaxed if true, undocumented parts present in the request will be ignored. If false,
     * a failure will occur if undocumented parts are present in the request.
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [RequestPartsScope] as receiver, allowing to add request part descriptors to
     * the snippet
     * @return the created snippet
     */
    fun requestParts(
        relaxed: Boolean = false,
        attributes: Map<String, Any?> = emptyMap(),
        configure: RequestPartsScope.() -> Unit
    ): RequestPartsSnippet = RequestPartsBuilder().apply(configure).build(relaxed, attributes)

    /**
     * Creates a new reusable [RequestPartFieldsSnippet] describing the fields in a part of a multipart request.
     * @param part the name of the part
     * @param relaxed if true, undocumented fields present in the request part payload will be ignored. If false,
     * a failure will occur if undocumented fields are present in the request part payload.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors to
     * the snippet
     * @return the created snippet
     */
    fun requestPartFields(
        part: String,
        relaxed: Boolean = false,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: FieldsScope.() -> Unit
    ): RequestPartFieldsSnippet = FieldsBuilder()
        .apply(configure)
        .buildRequestPartFields(part, relaxed, subsectionExtractor, attributes)

    /**
     * Creates a new reusable [RequestBodySnippet] describing the body of a request.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @return the created snippet
     */
    fun requestBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    ) : RequestBodySnippet = PayloadDocumentation.requestBody(subsectionExtractor, attributes)

    /**
     * Creates a new reusable [ResponseBodySnippet] describing the body of a response.
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @return the created snippet
     */
    fun responseBody(
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    ) : ResponseBodySnippet = PayloadDocumentation.responseBody(subsectionExtractor, attributes)

    /**
     * Creates a new reusable [RequestPartBodySnippet] describing the body of a part of a multipart request.
     * @param part the name of the part
     * @param subsectionExtractor an extractor allowing to document only a subsection of the actual payload. See
     * [org.springframework.restdocs.payload.PayloadDocumentation.beneathPath]
     * @param attributes arbitrary additional attributes for the snippet
     * @return the created snippet
     */
    fun requestPartBody(
        part: String,
        subsectionExtractor: PayloadSubsectionExtractor<*>? = null,
        attributes: Map<String, Any?> = emptyMap()
    ) : RequestPartBodySnippet = PayloadDocumentation.requestPartBody(part, subsectionExtractor, attributes)

    /**
     * Creates a new reusable [LinksSnippet] describing the hypermedia links of a RESTful resource.
     * @param relaxed if true, undocumented links present in the response will be ignored. If false,
     * a failure will occur if undocumented links are present in the response.
     * @param linkExtractor used to extract the links from the response. If null, links will be extracted from the
     * response automatically based on its content type
     * @param attributes arbitrary additional attributes for the snippet
     * @param configure a block taking a [LinksScope] as receiver, allowing to add link descriptors to the snippet
     * @return the created snippet
     */
    fun links(
        relaxed: Boolean = false,
        linkExtractor: LinkExtractor? = null,
        attributes: Map<String, Any?> = emptyMap(),
        configure: LinksScope.() -> Unit
    ): LinksSnippet = LinksBuilder().apply(configure).build(relaxed, linkExtractor, attributes)
}
