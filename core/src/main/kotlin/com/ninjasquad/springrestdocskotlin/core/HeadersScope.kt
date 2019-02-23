package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.headers.HeaderDescriptor

/**
 * Receiver of the extension function used to add header descriptors to headers snippets
 */
@RestDocumentationDslMarker
interface HeadersScope {

    /**
     * Creates a new header descriptor and adds it to the snippet being configured.
     * @param name the name of the header
     * @param description the description of the header
     * @param optional if true, marks the descriptor as optional
     * @param attributes arbitrary additional attributes for the descriptor
     *
     * @see Descriptors
     */
    fun add(
        name: String,
        description: String,
        optional: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Adds the given header descriptor to the snippet being configured
     * @param headerDescriptor the descriptor to add to the snippet
     */
    fun add(headerDescriptor: HeaderDescriptor)
}
