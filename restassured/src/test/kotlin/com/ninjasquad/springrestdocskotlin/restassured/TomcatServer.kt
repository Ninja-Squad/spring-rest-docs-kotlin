package com.ninjasquad.springrestdocskotlin.restassured

import com.fasterxml.jackson.core.JsonProcessingException
import org.apache.catalina.LifecycleException
import org.apache.catalina.startup.Tomcat
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal class TomcatServer(private val jsonResponse: String) {

    private val tomcat: Tomcat = Tomcat()

    var port: Int = 0
        private set

    fun start() {
        this.tomcat.connector.port = 0
        val context = this.tomcat.addContext("/", null)
        this.tomcat.addServlet("/", "test", TestServlet(jsonResponse))
        context.addServletMappingDecoded("/", "test")
        this.tomcat.start()
        this.port = this.tomcat.connector.localPort
    }

    fun stop() {
        try {
            this.tomcat.stop()
        } catch (ex: LifecycleException) {
            throw RuntimeException(ex)
        }
    }

    /**
     * [HttpServlet] used to handle requests in the tests.
     */
    private class TestServlet(private val jsonResponse: String) : HttpServlet() {

        @Throws(ServletException::class, IOException::class)
        override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
            respondWithJson(response)
        }

        @Throws(IOException::class, JsonProcessingException::class)
        private fun respondWithJson(response: HttpServletResponse) {
            response.characterEncoding = "UTF-8"
            response.contentType = "application/json"
            val content = HashMap<String, Any>()
            content["id"] = 42L
            content["firstName"] = "John"
            content["lastName"] = "Doe"
            response.writer.println(jsonResponse)
            response.flushBuffer()
        }

    }
}
