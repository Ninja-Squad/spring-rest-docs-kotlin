package com.ninjasquad.springrestdocskotlin.mockmvc

import com.ninjasquad.springrestdocskotlin.core.DocumentationScope
import com.ninjasquad.springrestdocskotlin.core.documentationScope
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

/**
 * Create a [MockHttpServletRequestBuilder] for a GET request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.get] which avoids accidentally using
 * [MockMvcRequestBuilders.get].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the GET request
 */
fun docGet(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.get(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a POST request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.post] which avoids accidentally using
 * [MockMvcRequestBuilders.post].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the POST request
 */
fun docPost(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.post(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a PUT request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.put] which avoids accidentally using
 * [MockMvcRequestBuilders.put].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the PUT request
 */
fun docPut(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.put(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a DELETE request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.delete] which avoids accidentally using
 * [MockMvcRequestBuilders.delete].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the DELETE request
 */
fun docDelete(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.delete(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a HEAD request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.head] which avoids accidentally using
 * [MockMvcRequestBuilders.head].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the HEAD request
 */
fun docHead(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.head(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for an OPTIONS request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.options] which avoids accidentally using
 * [MockMvcRequestBuilders.options].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the POST request
 */
fun docOptions(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.options(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a PATCH request. The URL template
 * will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.patch] which avoids accidentally using
 * [MockMvcRequestBuilders.patch].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the PATCH request
 */
fun docPatch(urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.patch(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a multipart request. The url
 * template will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.fileUpload].
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the file upload request
 */
fun docFileUpload(urlTemplate: String, vararg urlVariables: Any?): MockMultipartHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.fileUpload(urlTemplate, *urlVariables)

/**
 * Create a [MockHttpServletRequestBuilder] for a request with the given HTTP
 * method. The URL template will be captured and made available for documentation.
 * This is an alias for [RestDocumentationRequestBuilders.request].
 * @param httpMethod the HTTP method
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the builder for the request
 */
fun docRequest(httpMethod: HttpMethod, urlTemplate: String, vararg urlVariables: Any?): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.request(httpMethod, urlTemplate, *urlVariables)

/**
 * Adds a MockMVC result handler to the chain of handlers applied to the result of a MockMVC request,
 * which allows documenting the request and its response.
 *
 * Example usage:
 * @sample com.ninjasquad.springrestdocskotlin.mockmvc.andDocumentExample
 *
 * @param identifier an identifier for the API call that is being documented
 * @param configure an extension function taking a [DocumentationScope] as receiver,
 * and which allows adding and configuring documentation snippets to the documentation
 * @return the ResultActions, allowing to apply additional result handlers
 */
fun ResultActions.andDocument(identifier: String, configure: DocumentationScope.() -> Unit): ResultActions =
    andDo(documentationScope(identifier).apply(configure).toResultHandler())

private fun DocumentationScope.toResultHandler(): ResultHandler =
    MockMvcRestDocumentation.document(
        identifier,
        requestPreprocessor,
        responsePreprocessor,
        *snippets.toTypedArray()
    )
