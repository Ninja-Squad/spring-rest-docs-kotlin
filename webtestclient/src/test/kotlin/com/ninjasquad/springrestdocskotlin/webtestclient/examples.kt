package com.ninjasquad.springrestdocskotlin.webtestclient

import org.springframework.test.web.reactive.server.WebTestClient

private fun andDocumentExample(webTestClient: WebTestClient) {
    webTestClient.get()
        .uri("/api/users/{userId}", 42)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .andDocument("users/get") {
            pathParameters {
                add("userId", "The ID of the user to get")
            }
            responseFields {
                add("firstName", "The first name of the user")
                // ...
            }
        }
}
