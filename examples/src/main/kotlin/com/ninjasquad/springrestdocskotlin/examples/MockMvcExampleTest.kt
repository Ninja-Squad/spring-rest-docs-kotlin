package com.ninjasquad.springrestdocskotlin.examples

import com.ninjasquad.springrestdocskotlin.core.Snippets
import com.ninjasquad.springrestdocskotlin.mockmvc.andDocument
import com.ninjasquad.springrestdocskotlin.mockmvc.docGet
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc

/**
 * Example test code using MockMVC (annotations are missing: this is not a real test)
 */
class MockMvcExampleTest(val mockMvc: MockMvc) {

    /**
     * This snippet can be reused in several test methods
     */
    val reusableHeadersSnippet = Snippets.requestHeaders {
        add(HttpHeaders.AUTHORIZATION, "the basic auth authorization header")
    }

    fun `should get user`() {
        // using docGet makes sure you don't accidentally import `MockMvcRequestBuilder.get`instead of
        // `RestDocumentationRequestBuilders.get`
        mockMvc.docGet("/users/{userId}", 42L)
            .andExpect { status { isOk } }
            // andDocument is easier to type and read than andDo { document(...) }
            // and it takes a DocumentationScope extension function as argument, providing
            // easy, scoped access to several methods (snippet, requestParameters, etc.)
            .andDocument("users/get") {
                snippet(reusableHeadersSnippet)

                pathParameters {
                    add("userId", "The ID of the user to get")
                }

                responseFields {
                    add("firstName", "the first name of the user")
                    addSubsection("coordinates", "the geo-coordinates of the main location of the user")
                    add("address", "the main address of the user")
                    withPrefix("address.") {
                        add("street", "the street")
                        add("country", "the ISO country code")
                    }
                }
            }
    }
}
