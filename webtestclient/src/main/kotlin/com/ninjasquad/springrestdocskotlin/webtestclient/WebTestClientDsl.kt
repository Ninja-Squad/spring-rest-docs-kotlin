package com.ninjasquad.springrestdocskotlin.webtestclient

import com.ninjasquad.springrestdocskotlin.core.DocumentationScope
import com.ninjasquad.springrestdocskotlin.core.documentationScope
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.reactive.server.ExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.function.Consumer

/**
 * Adds a consumer to the [BodyContentSpec], which allows documenting the request and its response.
 *
 * Example usage:
 * @sample com.ninjasquad.springrestdocskotlin.webtestclient.andDocumentExample
 *
 * @param identifier an identifier for the API call that is being documented
 * @param configure an extension function taking a [DocumentationScope] as receiver,
 * and which allows adding and configuring documentation snippets to the documentation
 * @return the ResultActions, allowing to apply additional result handlers
 */
fun WebTestClient.BodyContentSpec.andDocument(
    identifier: String,
    configure: DocumentationScope.() -> Unit
): WebTestClient.BodyContentSpec =
    consumeWith(documentationScope(identifier).apply(configure).buildWebTestClient())

private fun <T : ExchangeResult> DocumentationScope.buildWebTestClient(): Consumer<T> =
    WebTestClientRestDocumentation.document(
        identifier,
        requestPreprocessor ?: OperationRequestPreprocessor { request -> request },
        responsePreprocessor ?: OperationResponsePreprocessor { response -> response },
        *snippets.toTypedArray()
    )
