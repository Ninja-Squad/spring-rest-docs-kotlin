package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.operation.preprocess.OperationPreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.UriModifyingOperationPreprocessor

/**
 * Implementation of [PreprocessorScope] that aggregates all the operation preprocessors
 */
internal open class PreprocessorBuilder : PreprocessorScope {
    internal val preprocessors = mutableListOf<OperationPreprocessor>()

    override fun prettyPrint() {
        add(Preprocessors.prettyPrint())
    }

    override fun removeHeaders(vararg headerNames: String) {
        add(Preprocessors.removeHeaders(*headerNames))
    }

    override fun removeMatchingHeaders(vararg headerNameRegexes: Regex) {
        add(Preprocessors.removeMatchingHeaders(*headerNameRegexes.map { it.pattern }.toTypedArray()))
    }

    override fun maskLinks(mask: String) {
        add(Preprocessors.maskLinks(mask))
    }

    override fun replaceRegex(regex: Regex, replacement: String) {
        add(
            Preprocessors.replacePattern(
                regex.toPattern(),
                replacement
            )
        )
    }

    override fun modifyUris(configure: UriModifyingOperationPreprocessor.() -> Unit) {
        add(Preprocessors.modifyUris().apply(configure))
    }

    override fun add(preprocessor: OperationPreprocessor) {
        preprocessors.add(preprocessor)
    }
}
