package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.headers.AbstractHeadersSnippet
import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.hypermedia.LinkDescriptor
import org.springframework.restdocs.hypermedia.LinkExtractor
import org.springframework.restdocs.hypermedia.LinksSnippet
import org.springframework.restdocs.payload.*
import org.springframework.restdocs.request.AbstractParametersSnippet
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.request.RequestPartsSnippet
import org.springframework.restdocs.snippet.TemplatedSnippet
import kotlin.reflect.KClass

val TemplatedSnippet.attributeMap: Map<String, Any?>
    get() = TemplatedSnippet::class.fieldByReflection(this, "attributes")

val AbstractParametersSnippet.relaxed: Boolean
    get() = AbstractParametersSnippet::class.fieldByReflection(this, "ignoreUndocumentedParameters")

val AbstractParametersSnippet.descriptors: Map<String, ParameterDescriptor>
    get() = AbstractParametersSnippet::class.fieldByReflection(this, "descriptorsByName")

val RequestPartsSnippet.relaxed: Boolean
    get() = RequestPartsSnippet::class.fieldByReflection(this, "ignoreUndocumentedParts")

val RequestPartsSnippet.descriptors: Map<String, RequestPartDescriptor>
    get() = RequestPartsSnippet::class.fieldByReflection(this, "descriptorsByName")

val AbstractFieldsSnippet.relaxed: Boolean
    get() = AbstractFieldsSnippet::class.fieldByReflection(this, "ignoreUndocumentedFields")

val AbstractFieldsSnippet.payloadSubsectionExtractor: PayloadSubsectionExtractor<*>?
    get() = AbstractFieldsSnippet::class.fieldByReflection(this, "subsectionExtractor")

val AbstractFieldsSnippet.descriptors: List<FieldDescriptor>
    get() = AbstractFieldsSnippet::class.fieldByReflection(this, "fieldDescriptors")

val AbstractHeadersSnippet.descriptors: List<HeaderDescriptor>
    get() = AbstractHeadersSnippet::class.fieldByReflection(this, "headerDescriptors")

val RequestPartFieldsSnippet.partName: String
    get() = RequestPartFieldsSnippet::class.fieldByReflection(this, "partName")

val AbstractBodySnippet.payloadSubsectionExtractor: PayloadSubsectionExtractor<*>?
    get() = AbstractBodySnippet::class.fieldByReflection(this, "subsectionExtractor")

val RequestPartBodySnippet.partName: String
    get() = RequestPartBodySnippet::class.fieldByReflection(this, "partName")

val LinksSnippet.relaxed: Boolean
    get() = LinksSnippet::class.fieldByReflection(this, "ignoreUndocumentedLinks")

val LinksSnippet.linkExtractor: LinkExtractor?
    get() = LinksSnippet::class.fieldByReflection(this, "linkExtractor")

val LinksSnippet.descriptors: Map<String, LinkDescriptor>
    get() = LinksSnippet::class.fieldByReflection(this, "descriptorsByRel")

@Suppress("UNCHECKED_CAST")
private fun <C: Any, T> KClass<C>.fieldByReflection(obj: C, name: String): T =
    java.declaredFields.find { it.name == name }!!
        .apply { isAccessible = true }
        .get(obj) as T
