package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.hypermedia.LinkDescriptor

/**
 * Receiver of the extension function used to add link descriptors to links snippets
 */
@RestDocumentationDslMarker
interface LinksScope {

    /**
     * Creates a new link descriptor and adds it to the snippet being configured.
     * @param rel the rel of the link
     * @param description the description of the link. Optional if `ignored` is true, or if the link has a `title`. In
     * that case, the title will be used as the description
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param attributes arbitrary additional attributes for the descriptor
     *
     * @see Descriptors
     */
    fun add(
        rel: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Adds the given link descriptor to the snippet being configured
     * @param linkDescriptor the descriptor to add to the snippet
     */
    fun add(linkDescriptor: LinkDescriptor)
}
