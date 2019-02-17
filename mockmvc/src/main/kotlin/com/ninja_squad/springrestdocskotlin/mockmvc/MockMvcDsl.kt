package com.ninja_squad.springrestdocskotlin.mockmvc

import com.ninja_squad.springrestdocskotlin.core.DocumentationScope
import com.ninja_squad.springrestdocskotlin.core.documentationScope
import org.springframework.http.HttpMethod
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultHandler
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder

fun docGet(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.get(urlTemplate, *urlVariables)

fun docPost(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.post(urlTemplate, *urlVariables)

fun docPut(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.put(urlTemplate, *urlVariables)

fun docDelete(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.delete(urlTemplate, *urlVariables)

fun docHead(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.head(urlTemplate, *urlVariables)

fun docOptions(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.options(urlTemplate, *urlVariables)

fun docPatch(urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.patch(urlTemplate, *urlVariables)

fun docFileUpload(urlTemplate: String, vararg urlVariables: Any): MockMultipartHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.fileUpload(urlTemplate, *urlVariables)

fun docRequest(httpMethod: HttpMethod, urlTemplate: String, vararg urlVariables: Any): MockHttpServletRequestBuilder =
    RestDocumentationRequestBuilders.request(httpMethod, urlTemplate, *urlVariables)

fun ResultActions.andDocument(identifier: String, configure: DocumentationScope.() -> Unit): ResultActions =
    andDo(documentationScope(identifier).apply(configure).toResultHandler())

private fun DocumentationScope.toResultHandler(): ResultHandler =
    MockMvcRestDocumentation.document(
        identifier,
        requestPreprocessor ?: OperationRequestPreprocessor { request -> request },
        responsePreprocessor ?: OperationResponsePreprocessor { response -> response },
        *snippets.toTypedArray()
    )
