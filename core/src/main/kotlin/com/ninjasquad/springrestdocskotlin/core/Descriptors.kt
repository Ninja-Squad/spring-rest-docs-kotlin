package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.hypermedia.HypermediaDocumentation
import org.springframework.restdocs.hypermedia.LinkDescriptor
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.SubsectionDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.snippet.Attributes

/**
 * Object serving as a factory for reusable descriptors, of all types.
 *
 * All methods take the `name`, `path` or `rel` of the descriptor to create (depending on the type) as first
 * argument, and the description as second argument. These two arguments are the most commonly used, and can be
 * passed as positional arguments or named arguments. Use what you prefer:.
 *
 * The other arguments are optional, and intended to be used as named arguments, to improve readability and to
 * promote forward compatibility:
 *
 * @sample com.ninjasquad.springrestdocskotlin.core.descriptorsNameAndDescriptionArgumentsExample
 * @sample com.ninjasquad.springrestdocskotlin.core.descriptorsOtherArgumentsDontExample
 * @sample com.ninjasquad.springrestdocskotlin.core.descriptorsOtherArgumentsDoExample
 *
 * @author JB Nizet
 */
object Descriptors {

    /**
     * Creates a new reusable [ParameterDescriptor] describing the parameter with the given `name`.
     * @param name the name of the parameter
     * @param description the description of the parameter. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param attributes arbitrary additional attributes for the descriptor
     * @return the created descriptor
     */
    fun parameter(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): ParameterDescriptor = RequestDocumentation.parameterWithName(name)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .attributes(*attributes.toAttributes())

    /**
     * Creates a new reusable [FieldDescriptor] describing the field with the given `path`.
     * @param path the name of the field
     * @param description the description of the field. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param type the type of the field. When documenting a JSON payload, the
     * [org.springframework.restdocs.payload.JsonFieldType] enumeration will
     * typically be used.
     * @param attributes arbitrary additional attributes for the descriptor
     * @return the created descriptor
     */
    fun field(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    ): FieldDescriptor = PayloadDocumentation.fieldWithPath(path)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .type(type)
        .attributes(*attributes.toAttributes())

    /**
     * Creates a new reusable [SubsectionDescriptor] describing the subsection (i.e. a field and all of its descendants)
     * with the given `path`.
     * @param path the path of the subsection
     * @param description the description of the subsection. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param type the type of the subsection. When documenting a JSON payload, the
     * [org.springframework.restdocs.payload.JsonFieldType] enumeration will
     * typically be used.
     * @param attributes arbitrary additional attributes for the descriptor
     * @return the created descriptor
     */
    fun subsection(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    ): SubsectionDescriptor = PayloadDocumentation.subsectionWithPath(path)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .type(type)
        .attributes(*attributes.toAttributes()) as SubsectionDescriptor

    /**
     * Creates a new reusable [HeaderDescriptor] describing the header with the given `name`.
     * @param name the name of the header
     * @param description the description of the header
     * @param optional if true, marks the descriptor as optional
     * @param attributes arbitrary additional attributes for the descriptor
     * @return the created descriptor
     */
    fun header(
        name: String,
        description: String,
        optional: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): HeaderDescriptor = HeaderDocumentation.headerWithName(name)
        .description(description)
        .apply {
            if (optional) optional()
        }
        .attributes(*attributes.toAttributes())

    /**
     * Creates a new reusable [RequestPartDescriptor] describing the part of a multipart request with the given `name`.
     * @param name the name of the part
     * @param description the description of the field. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param attributes arbitrary additional attributes for the descriptor
     * @return the created descriptor
     */
    fun requestPart(
        name: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): RequestPartDescriptor = RequestDocumentation.partWithName(name)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .attributes(*attributes.toAttributes())

    /**
     * Creates a new reusable [LinkDescriptor] describing the hypermedia link with the given `rel`.
     * @param rel the rel of the link
     * @param description the description of the link. Optional if `ignored` is true, or if the link has a `title`. In
     * that case, the title will be used as the description
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param attributes arbitrary additional attributes for the descriptor
     * @return the created descriptor
     */
    fun link(
        rel: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        attributes: Map<String, Any?> = emptyMap()
    ): LinkDescriptor = HypermediaDocumentation.linkWithRel(rel)
        .description(description)
        .apply {
            if (optional) optional()
            if (ignored) ignored()
        }
        .attributes(*attributes.toAttributes())
}

private fun Map<String, Any?>.toAttributes() = map { Attributes.key(it.key).value(it.value) }.toTypedArray()
