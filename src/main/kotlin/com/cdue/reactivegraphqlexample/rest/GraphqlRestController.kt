package com.cdue.reactivegraphqlexample.rest

import com.cdue.reactivegraphqlexample.exception.HttpNotFoundException
import graphql.servlet.SimpleGraphQLHttpServlet
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/")
class GraphqlRestController(private val graphQLServlet: SimpleGraphQLHttpServlet) {

    /*@RequestMapping("/graphql")
    @Throws(Exception::class)
    fun graphql(request: HttpServletRequest, response: HttpServletResponse) {
        graphQLServlet.service(request, response)
    }*/

    @RequestMapping("/graphiql")
    @Throws(IOException::class)
    fun graphiql(response: HttpServletResponse) {
        response.contentType = "text/html; charset=UTF-8"

        val graphiqlFile = ClassPathXmlApplicationContext().getResource("classpath:/graphql/graphiql.html")
        if (graphiqlFile.isReadable) {
            val template = graphiqlFile.inputStream.bufferedReader().use { it.readText() }

            val html = template
                    .replace("\${graphqlEndpoint}", "/graphql")
                    .replace("\${pageTitle}", "Reactive GraphQL example - GraphiQL")

            response.outputStream.write(html.toByteArray())
        } else {
            throw HttpNotFoundException()
        }
    }
}