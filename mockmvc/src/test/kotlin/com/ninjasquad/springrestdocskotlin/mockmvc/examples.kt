package com.ninjasquad.springrestdocskotlin.mockmvc

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

private fun andDocumentExample(mockMvc: MockMvc) {
    mockMvc.perform(docGet("/api/users/{userId}", 42))
        .andExpect(status().isOk)
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
