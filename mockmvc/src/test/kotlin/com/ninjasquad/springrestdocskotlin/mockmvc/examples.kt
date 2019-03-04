package com.ninjasquad.springrestdocskotlin.mockmvc

import org.springframework.test.web.servlet.MockMvc

private fun andDocumentExample(mockMvc: MockMvc) {
    mockMvc.docGet("/api/users/{userId}", 42)
        .andExpect { status { isOk } }
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
