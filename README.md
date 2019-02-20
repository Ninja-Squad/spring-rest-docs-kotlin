# Spring-REST-Docs-Kotlin

[![CircleCI](https://circleci.com/gh/Ninja-Squad/spring-rest-docs-kotlin.svg?style=svg)](https://circleci.com/gh/Ninja-Squad/spring-rest-docs-kotlin)

For the time being, this is an experimentation of a Kotlin-based DSL for [Spring-REST-Docs](https://spring.io/projects/spring-restdocs)

If you're interested to understand how the DSL looks like, you can
have a look at [the examples](examples/src/main/kotlin/com/ninja_squad/springrestdocskotlin/examples/core).

## Main goals

- Be able to do everything we can do with the native Java DSL, but in a more Kotlin-esque way.
  This includes reusable descriptors, reusable snippets, relaxed snippets, attributes, preprocessors.
- Have an easier to discover, more intuitive API: almost no static imports necessary. 
  Once you know you must use `andDocument`, the API should be discoverable by using code-completion
- Provide scopes that can be the targets for extension function. See [this file](examples/src/main/kotlin/com/ninja_squad/springrestdocskotlin/examples/core/ExtensionFunctionsExampleTest.kt) for an example
