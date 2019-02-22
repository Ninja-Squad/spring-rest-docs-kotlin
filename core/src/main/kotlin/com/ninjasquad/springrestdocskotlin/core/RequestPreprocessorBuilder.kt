package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.ParametersModifyingOperationPreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

/**
 * Implementation of [RequestPreprocessorScope] that aggregates all the operation preprocessors and builds
 * an [OperationRequestPreprocessor]
 */
internal class RequestPreprocessorBuilder : PreprocessorBuilder(),
    RequestPreprocessorScope {
    override fun modifyParameters(configure: ParametersModifyingOperationPreprocessor.() -> Unit) {
        add(Preprocessors.modifyParameters().apply(configure))
    }

    fun build(): OperationRequestPreprocessor =
        Preprocessors.preprocessRequest(*preprocessors.toTypedArray())
}
