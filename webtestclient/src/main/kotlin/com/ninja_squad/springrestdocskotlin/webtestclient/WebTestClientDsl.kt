package com.ninja_squad.springrestdocskotlin.webtestclient

import com.ninja_squad.springrestdocskotlin.core.DocumentationScope
import com.ninja_squad.springrestdocskotlin.core.documentationScope
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.reactive.server.ExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.function.Consumer

private fun <T : ExchangeResult> DocumentationScope.buildWebTestClient(): Consumer<T> =
    WebTestClientRestDocumentation.document(
        identifier,
        requestPreprocessor ?: OperationRequestPreprocessor { request -> request },
        responsePreprocessor ?: OperationResponsePreprocessor { response -> response },
        *snippets.toTypedArray()
    )

fun WebTestClient.BodyContentSpec.andDocument(
    identifier: String,
    configure: DocumentationScope.() -> Unit
): WebTestClient.BodyContentSpec =
    consumeWith(documentationScope(identifier).apply(configure).buildWebTestClient())
