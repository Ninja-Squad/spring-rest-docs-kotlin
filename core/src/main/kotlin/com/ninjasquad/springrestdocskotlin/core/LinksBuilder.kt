package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.hypermedia.HypermediaDocumentation
import org.springframework.restdocs.hypermedia.LinkDescriptor
import org.springframework.restdocs.hypermedia.LinkExtractor
import org.springframework.restdocs.hypermedia.LinksSnippet

/**
 * Implementation of [LinksScope], allowing to build instances of [LinksSnippet]
 */
internal class LinksBuilder : LinksScope {
    private val links = mutableListOf<LinkDescriptor>()

    override fun add(
        rel: String,
        description: String?,
        optional: Boolean,
        ignored: Boolean,
        attributes: Map<String, Any?>
    ) = add(Descriptors.link(rel, description, optional, ignored, attributes))

    override fun add(linkDescriptor: LinkDescriptor) {
        links.add(linkDescriptor)
    }

    fun build(
        relaxed: Boolean,
        linkExtractor: LinkExtractor?,
        attributes: Map<String, Any?>
    ): LinksSnippet =
        if (relaxed) {
            linkExtractor
                ?.let {
                    HypermediaDocumentation.relaxedLinks(
                        linkExtractor,
                        attributes,
                        links
                    )
                }
                ?: HypermediaDocumentation.relaxedLinks(attributes, links)
        } else {
            linkExtractor
                ?.let {
                    HypermediaDocumentation.links(
                        linkExtractor,
                        attributes,
                        links
                    )
                }
                ?: HypermediaDocumentation.links(attributes, links)
        }
}
