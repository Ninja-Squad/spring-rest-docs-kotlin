package com.ninja_squad.springrestdocskotlin.restassured

import com.ninja_squad.springrestdocskotlin.core.DocumentationScope
import com.ninja_squad.springrestdocskotlin.core.documentationScope
import io.restassured.specification.RequestSpecification
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation
import org.springframework.restdocs.restassured3.RestDocumentationFilter

// RestAssured specific
fun RequestSpecification.andDocument(identifier: String, configure: DocumentationScope.() -> Unit): RequestSpecification =
    filter(documentationScope(identifier).apply(configure).toFilter())

private fun DocumentationScope.toFilter(): RestDocumentationFilter =
    RestAssuredRestDocumentation.document(
        identifier,
        requestPreprocessor ?: OperationRequestPreprocessor { request -> request },
        responsePreprocessor ?: OperationResponsePreprocessor { response -> response },
        *snippets.toTypedArray()
    )
