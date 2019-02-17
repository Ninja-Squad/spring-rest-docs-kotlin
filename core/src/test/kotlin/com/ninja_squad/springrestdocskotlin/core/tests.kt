package com.ninja_squad.springrestdocskotlin.core

import org.springframework.restdocs.request.AbstractParametersSnippet
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.snippet.TemplatedSnippet

val TemplatedSnippet.attributes: Map<String, Any?>
    get() = TemplatedSnippet::class.java.declaredFields
        .find { it.name == "attributes" }!!
        .apply { isAccessible = true }
        .get(this) as Map<String, Any?>

val AbstractParametersSnippet.relaxed: Boolean
    get() = AbstractParametersSnippet::class.java.declaredFields
        .find { it.name == "ignoreUndocumentedParameters" }!!
        .apply { isAccessible = true }
        .get(this) as Boolean

val AbstractParametersSnippet.descriptors: Map<String, ParameterDescriptor>
    get() = AbstractParametersSnippet::class.java.declaredMethods
        .find { it.name == "getParameterDescriptors" }!!
        .apply { isAccessible = true }
        .invoke(this) as Map<String, ParameterDescriptor>
