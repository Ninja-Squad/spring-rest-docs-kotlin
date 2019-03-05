package com.ninjasquad.springrestdocskotlin.mockmvc

import com.ninjasquad.springrestdocskotlin.core.DocumentationScope
import com.ninjasquad.springrestdocskotlin.core.documentationScope
import org.springframework.http.HttpMethod
import org.springframework.restdocs.generate.RestDocumentationGenerator
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.*

/**
 * Create a [ResultActionsDsl] for a GET request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.get] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docGet(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return get(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a POST request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.post] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docPost(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return post(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a PUT request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.put] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docPut(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return put(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a DELETE request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.delete] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docDelete(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return delete(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a HEAD request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.head] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docHead(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return head(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}


/**
 * Create a [ResultActionsDsl] for an OPTIONS request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.options] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docOptions(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return options(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a PATCH request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.patch] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docPatch(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return patch(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a multipart request. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.multipart] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docMultipart(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return multipart(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

/**
 * Create a [ResultActionsDsl] for a request with the given HTTP method. The URL template
 * will be captured and made available for documentation.
 * Use this method instead of [MockMvc.multipart] in order to be able to document the URL template and path parameters
 * @param urlTemplate a URL template; the resulting URL will be encoded
 * @param urlVariables zero or more URL variables
 * @return the DSL allowing to apply expectations, handlers, and to document the request
 */
fun MockMvc.docRequest(
    method: HttpMethod,
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return request(method, urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate);
        dsl()
    }
}

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
fun ResultActionsDsl.andDocument(identifier: String, configure: DocumentationScope.() -> Unit): ResultActionsDsl =
    andDo { handle(documentationScope(identifier).apply(configure).toResultHandler()) }

private fun DocumentationScope.toResultHandler(): ResultHandler =
    MockMvcRestDocumentation.document(
        identifier,
        requestPreprocessor,
        responsePreprocessor,
        *snippets.toTypedArray()
    )
