package com.ninja_squad.springrestdocskotlin.examples.core

import com.ninja_squad.springrestdocskotlin.core.requestHeadersSnippet
import com.ninja_squad.springrestdocskotlin.core.subsection
import com.ninja_squad.springrestdocskotlin.restassured.andDocument
import io.restassured.RestAssured
import io.restassured.specification.RequestSpecification
import org.springframework.http.HttpHeaders


/**
 * Example test code using RestAssured (annotations are missing: this is not a real test)
 */
class RestAssuredExampleTest {
    /**
     * This snippet can be reused in several test methods
     */
    val reusableHeadersSnippet = requestHeadersSnippet {
        add(HttpHeaders.AUTHORIZATION, "the basic auth authorization header")
    }

    // How this variable is initialized is not the point of this example
    private lateinit var spec: RequestSpecification

    fun `should search`() {
        // andDocument is easier to type and read than .filter(document(...))
        // and it takes a DocumentationScope extension function as argument, providing
        // easy, scoped access to several methods (snippet, requestParameters, etc.)
        RestAssured.given(spec).andDocument("users/search") {
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
        }.get("/users?page=1&query=john").then().assertThat().statusCode(200)
    }
}
