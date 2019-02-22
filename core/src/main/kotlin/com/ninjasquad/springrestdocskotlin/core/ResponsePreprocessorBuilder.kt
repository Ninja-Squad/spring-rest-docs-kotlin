package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors

/**
 * Implementation of [ResponsePreprocessorScope] that aggregates all the operation preprocessors and builds
 * an [OperationResponsePreprocessor]
 */
internal class ResponsePreprocessorBuilder : PreprocessorBuilder(),
    ResponsePreprocessorScope {
    fun build(): OperationResponsePreprocessor =
        Preprocessors.preprocessResponse(*preprocessors.toTypedArray())
}
