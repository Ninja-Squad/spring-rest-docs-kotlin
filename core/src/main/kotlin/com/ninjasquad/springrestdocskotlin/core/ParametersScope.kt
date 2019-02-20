package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.request.ParameterDescriptor

/**
 * Receiver of the extension function used to add parameter descriptors to parameters snippets
 */
interface ParametersScope {

    /**
     * Creates a new parameter descriptor and adds it to the snippet being configured.
     * @param name the name of the parameter
     * @param description the description of the parameter. Optional if `ignored` is true
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
     * Adds the given parameter descriptor to the snippet being configured
     * @param parameterDescriptor the descriptor to add to the snippet
     */
    fun add(parameterDescriptor: ParameterDescriptor)
}

