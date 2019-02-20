package com.ninjasquad.springrestdocskotlin.core

private fun descriptorsNameAndDescriptionArgumentsExample() {
    // positional
    val firstName = Descriptors.field("firstName", "the first name of the user")
    // named
    val lastName = Descriptors.field(path = "firstName", description = "the first name of the user")
}

private fun descriptorsOtherArgumentsDontExample() {
    // DON'T:
    val address = Descriptors.subsection("address", "the address of the user", true)
}

private fun descriptorsOtherArgumentsDoExample() {
    // DO:
    val address = Descriptors.subsection("address", "the address of the user", optional = true)
    val phone = Descriptors.field("phone", ignored = true)
}
