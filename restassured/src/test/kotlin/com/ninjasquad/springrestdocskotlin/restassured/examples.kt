package com.ninjasquad.springrestdocskotlin.restassured

import io.restassured.RestAssured.given
import io.restassured.specification.RequestSpecification

private fun andDocumentExample(documentationSpec: RequestSpecification, port: Int) {
    given(documentationSpec)
        .port(port)
        .andDocument("users/get") {
            pathParameters {
                add("userId", "The ID of the user to get")
            }
            responseFields {
                add("firstName", "The first name of the user")
                // ...
            }
        }
        .get("/users/{userId}", 42)
        .then().statusCode(200)
}
