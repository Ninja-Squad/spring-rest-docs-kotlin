package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.payload.FieldDescriptor

/**
 * Receiver of the extension function used to add field descriptors to fields snippets
 */
@RestDocumentationDslMarker
interface FieldsScope {

    /**
     * Creates a new field descriptor and adds it to the snippet being configured.
     * @param path the name of the field
     * @param description the description of the field. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param type the type of the field. When documenting a JSON payload, the
     * [org.springframework.restdocs.payload.JsonFieldType] enumeration will
     * typically be used.
     * @param attributes arbitrary additional attributes for the descriptor
     *
     * @see Descriptors
     */
    fun add(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Creates a new subsection descriptor and adds it to the snippet being configured.
     * @param path the path of the subsection
     * @param description the description of the subsection. Optional if `ignored` is true
     * @param optional if true, marks the descriptor as optional
     * @param ignored if true, marks the descriptor as ignored, i.e. not included in the documentation
     * @param type the type of the subsection. When documenting a JSON payload, the
     * [org.springframework.restdocs.payload.JsonFieldType] enumeration will
     * typically be used.
     * @param attributes arbitrary additional attributes for the descriptor
     *
     * @see Descriptors
     */
    fun addSubsection(
        path: String,
        description: String? = null,
        optional: Boolean = false,
        ignored: Boolean = false,
        type: Any? = null,
        attributes: Map<String, Any?> = emptyMap()
    )

    /**
     * Adds the given field descriptor to the snippet being configured
     * @param fieldDescriptor the descriptor to add to the snippet
     */
    fun add(fieldDescriptor: FieldDescriptor)

    /**
     * Adds all the field descriptors configured by the given `configure` block to the snippet being configured
     * after having prepended the given prefix to their path. This allows avoiding the repetition of a common
     * prefix, and show the tree structure of the fields. For example:
     * @sample withPrefixExample
     *
     * which is equivalent to
     *
     * @sample withoutPrefixExample
     *
     * @param prefix the prefix to prepend to all the field descriptors added inside the scope of this function
     * @param configure a block taking a [FieldsScope] as receiver, allowing to add field descriptors
     */
    fun withPrefix(prefix: String, configure: FieldsScope.() -> Unit)
}

private fun withPrefixExample() {
    val fields = Snippets.responseFields {
        add("firstName", "the first name of the user")
        add("lastName", "the last name of the user")
        withPrefix("address.") {
            add("city", "the city of the address of the user")
            add("country", "the ISO country code of the address of the user")
        }
    }
}

private fun withoutPrefixExample() {
    val fields = Snippets.responseFields {
        add("firstName", "the first name of the user")
        add("lastName", "the last name of the user")
        add("address.city", "the city of the address of the user")
        add("address.country", "the ISO country code of the address of the user")
    }
}
