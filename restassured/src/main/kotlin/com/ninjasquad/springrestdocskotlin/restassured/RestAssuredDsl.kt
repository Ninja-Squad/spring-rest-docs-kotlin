package com.ninjasquad.springrestdocskotlin.restassured

import com.ninjasquad.springrestdocskotlin.core.DocumentationScope
import com.ninjasquad.springrestdocskotlin.core.documentationScope
import io.restassured.specification.RequestSpecification
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation
import org.springframework.restdocs.restassured3.RestDocumentationFilter

/**
 * Adds a filer to the [RequestSpecification], which allows documenting the request and its response.
 *
 * Example usage:
 * @sample com.ninjasquad.springrestdocskotlin.restassured.andDocumentExample
 *
 * @param identifier an identifier for the API call that is being documented
 * @param configure an extension function taking a [DocumentationScope] as receiver,
 * and which allows adding and configuring documentation snippets to the documentation
 * @return the RequestSpecification
 */
fun RequestSpecification.andDocument(identifier: String, configure: DocumentationScope.() -> Unit): RequestSpecification =
    filter(documentationScope(identifier).apply(configure).toFilter())

private fun DocumentationScope.toFilter(): RestDocumentationFilter =
    RestAssuredRestDocumentation.document(
        identifier,
        requestPreprocessor,
        responsePreprocessor,
        *snippets.toTypedArray()
    )
