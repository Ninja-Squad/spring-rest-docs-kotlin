package com.ninja_squad.springrestdocskotlin.examples.core

import com.ninja_squad.springrestdocskotlin.core.requestHeadersSnippet
import com.ninja_squad.springrestdocskotlin.core.subsection
import com.ninja_squad.springrestdocskotlin.webtestclient.andDocument
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * Example test code using MockMVC (annotations are missing: this is not a real test)
 */
class WebTestClientExampleTest(val webTestClient: WebTestClient) {
    /**
     * This snippet can be reused in several test methods
     */
    val reusableHeadersSnippet = requestHeadersSnippet {
        add(HttpHeaders.AUTHORIZATION, "the basic auth authorization header")
    }

    fun `should search`() {
        webTestClient.get().uri("/users?page=1&query=john").exchange()
            .expectStatus().isOk()
            .expectBody()
            // andDocument is easier to type and read than consumeWith(document(...))
            // and it takes a DocumentationScope extension function as argument, providing
            // easy, scoped access to several methods (snippet, requestParameters, etc.)
            .andDocument("users/search") {
                snippet(reusableHeadersSnippet)

                requestParameters {
                    add("query", "The full-text query, case-insensitive")
                    add("page", "the page number, starting at 0", optional = true)
                }

                responseFields {
                    add("firstName", "the first name of the user")
                    subsection("coordinates", "the geo-coordinates of the main location of the user")
                    add("address", "the main address of the user")
                    withPrefix("address.") {
                        add("street", "the street")
                        add("country", "the ISO country code")
                    }
                }

                links(relaxed = true) {
                    add("next", "the link to the next page, if any", optional = true)
                }
            }
    }
}
