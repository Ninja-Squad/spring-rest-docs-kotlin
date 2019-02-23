package com.ninjasquad.springrestdocskotlin.core

import org.springframework.restdocs.operation.preprocess.ParametersModifyingOperationPreprocessor

/**
 * Interface used as a receiver of the extension functions used to configure request preprocessors
 */
interface RequestPreprocessorScope : PreprocessorScope {
    fun modifyParameters(configure: ParametersModifyingOperationPreprocessor.() -> Unit)
}
