package com.ninjasquad.springrestdocskotlin.examples

import com.ninjasquad.springrestdocskotlin.core.*
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.payload.PayloadDocumentation.beneathPath
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.request.RequestParametersSnippet

/**
 * A reusable request parameters snippet.
 */
val exampleParameters: RequestParametersSnippet = requestParametersSnippet {
    // positional
    add("page", "the page to retrieve (starts at 0)")

    // named
    add(name = "sort", description = "the property to sort on")

    // mixed, optional parameter
    add("query", "The full-text query", optional = true)

    // with attributes
    add("foo", "lore ipsum", attributes = mapOf("key" to "value"))
}

/**
 * A reusable response fields snippet, relaxed, for the fields under the path "body".
 */
val exampleFields: ResponseFieldsSnippet = responseFieldsSnippet(
    relaxed = true,
    subsectionExtractor = beneathPath("body")
) {
    // positional
    add("id", "the user ID")

    // named
    add(path = "firstName", description = "the last name of the user")

    // mixed, optional field
    add("avatarUrl", "URL of the avatar image of the user", optional = true)

    // with attributes
    add("lastName", "The last name of the user", attributes = mapOf("key" to "value"))

    // subsection
    subsection("security", "The security-related properties of the user")

    // nested
    withPrefix("address.") {
        add("street", "The street")
        add("country", "The country", optional = true)
    }
}.andWithPrefix("coordinates",
    field("latitude", "The latitude of the main location of the user"),
    field("longitude", "The longitude of the main location of the user")
)

/**
 * A reusable request parts snippet
 */
val exampleRequestParts = requestPartsSnippet {
    add("file", "The uploaded file", optional = true)
}

/**
 * A reusable headers snippet
 */
val exampleHeaders = requestHeadersSnippet {
    add(HttpHeaders.AUTHORIZATION, "The JSON Web Token prefixed with `Bearer `", optional = true)
}

