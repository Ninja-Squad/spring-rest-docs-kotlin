package com.ninjasquad.springrestdocskotlin.examples

import com.ninjasquad.springrestdocskotlin.core.requestHeadersSnippet
import com.ninjasquad.springrestdocskotlin.core.subsection
import com.ninjasquad.springrestdocskotlin.restassured.andDocument
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

    fun `should get user`() {
        // andDocument is easier to type and read than .filter(document(...))
        // and it takes a DocumentationScope extension function as argument, providing
        // easy, scoped access to several methods (snippet, requestParameters, etc.)
        RestAssured.given(spec).andDocument("users/get") {
            snippet(reusableHeadersSnippet)

            pathParameters {
                add("userId", "The ID of the user to get")
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
        }.get("/users/42").then().assertThat().statusCode(200)
    }
}
