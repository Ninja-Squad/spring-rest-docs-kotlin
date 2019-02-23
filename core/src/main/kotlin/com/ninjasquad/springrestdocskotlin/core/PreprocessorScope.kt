package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.operation.preprocess.OperationPreprocessor
import org.springframework.restdocs.operation.preprocess.UriModifyingOperationPreprocessor

/**
 * Interface used as a receiver of the extension functions used to configure request and response preprocessors
 */
@RestDocumentationDslMarker
interface PreprocessorScope {

    /**
     * Adds a preprocessor that will pretty print the request or response
     */
    fun prettyPrint()

    /**
     * Adds a preprocessor that will remove any header from the request or response with a name that is equal to
     * one of the given header names.
     * @param headerNames the header names
     */
    fun removeHeaders(vararg headerNames: String)

    /**
     * Adds a preprocessor that will remove any headers from the request or response with a name that matches
     * one of the given regular expressions.
     * @param headerNameRegexes the header name regular expressions
     */
    fun removeMatchingHeaders(vararg headerNameRegexes: Regex)

    /**
     * Adds a preprocessor that will mask the href of hypermedia
     * links in the request or response.
     * @param mask the link mask
     */
    fun maskLinks(mask: String = "...")

    /**
     * Adds a preprocessor that will modify the content of the request or response by replacing occurrences of
     * the given `regex` with the given `replacement`.
     * @param regex the regex
     * @param replacement the replacement
     */
    fun replaceRegex(regex: Regex, replacement: String)

    /**
     * Adds a preprocessor that will modify URIs in the request or response by changing one or more of their
     * host, scheme, and port.
     * @param configure the block allowing to configure how the URI will be modified
     */
    fun modifyUris(configure: UriModifyingOperationPreprocessor.() -> Unit)

    /**
     * Adds an existing preprocessor that will modify the request or response.
     * @param preprocessor the preprocessor
     */
    fun add(preprocessor: OperationPreprocessor)
}
