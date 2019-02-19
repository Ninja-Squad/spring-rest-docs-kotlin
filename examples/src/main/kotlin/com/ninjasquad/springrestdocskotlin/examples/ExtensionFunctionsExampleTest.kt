package com.ninjasquad.springrestdocskotlin.examples

import com.ninjasquad.springrestdocskotlin.core.DocumentationScope
import com.ninjasquad.springrestdocskotlin.core.FieldsScope
import com.ninjasquad.springrestdocskotlin.core.ParametersScope
import com.ninjasquad.springrestdocskotlin.mockmvc.andDocument
import com.ninjasquad.springrestdocskotlin.mockmvc.docGet
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

fun DocumentationScope.pageRequestParameters(configure: ParametersScope.() -> Unit) {
    requestParameters {
        configure()
        add("page", "the page number, starting at 0", optional = true)
    }
}

fun DocumentationScope.pageResponseFields(configure: FieldsScope.() -> Unit) {
    responseFields {
        add("content", "the elements of the page")
        withPrefix("content[].") {
            configure()
        }
        add("number", "the page number, starting at 0")
        add("size", "the page size")
        add("totalElements", "the total number of elements")
        add("totalPages", "the total number of pages")
    }
}

/**
 * Example showing how we can use extension functions on the various scopes to be DRY and still very readable
 * @author JB Nizet
 */
class PaginationExampleTest(val mockMvc: MockMvc) {

    fun `should search users`() {
        // using docGet makes sure you don't accidentally import `MockMvcRequestBuilder.get`instead of
        // `RestDocumentationRequestBuilders.get`
        mockMvc.perform(docGet("/users").param("query", "john").param("page", "1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            // andDocument is easier to type and read than andDo(document(...))
            // and it takes a DocumentationScope extension function as argument, providing
            // easy, scoped access to several methods (snippet, requestParameters, etc.)
            .andDocument("users/search") {
                pageRequestParameters {
                    add("query", "The full-text query, case-insensitive")
                }

                pageResponseFields {
                    add("id", "the ID of the user")
                    add("firstName", "the first name of the user")
                    add("lastName", "the last name of the user")
                }

                links(relaxed = true) {
                    add("next", "the link to the next page, if any", optional = true)
                }
            }
    }
}
