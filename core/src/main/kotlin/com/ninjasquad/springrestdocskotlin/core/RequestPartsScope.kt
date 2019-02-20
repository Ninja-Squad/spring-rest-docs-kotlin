package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.request.RequestPartDescriptor

/**
 * Receiver of the extension function used to add request part descriptors to request parts snippets
 */
interface RequestPartsScope {

    /**
     * Creates a new request part descriptor and adds it to the snippet being configured.
     * @param name the name of the part
     * @param description the description of the field. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param attributes arbitrary additional attributes for the descriptor
     *
     * @see Descriptors
     */
    fun add(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Adds the given request part descriptor to the snippet being configured
     * @param requestPartDescriptor the descriptor to add to the snippet
     */
    fun add(requestPartDescriptor: RequestPartDescriptor)
}
