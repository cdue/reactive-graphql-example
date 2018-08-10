package com.cdue.reactivegraphqlexample.graphql.config

import com.cdue.reactivegraphqlexample.exception.HttpNotFoundException
import com.fasterxml.jackson.databind.type.MapType
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import graphql.ExecutionInput.newExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.*
import java.net.URLDecoder

@Configuration
class GraphqlRouter(private val schemaConfiguration: SchemaConfiguration) {

  val graphQLMediaType = MediaType.parseMediaType("application/GraphQL")
  private val schema = schemaConfiguration.loadSchema()

  @Bean
  fun routes() = router {
    GET("/graphiql", serveStatic(ClassPathResource("/graphql/graphiql.html") /*getGraphiql(), MediaType.TEXT_HTML*/))
    (POST("/graphql") or GET("/graphql")).invoke { request: ServerRequest ->
      getGraphQLParameters(request)
        .flatMap { executeGraphQLQuery(it) }
        .flatMap { ok().syncBody(it) }
        .switchIfEmpty(badRequest().build())
    }
  }

  private fun getGraphiql(): Resource {

    val graphiqlFile = ClassPathResource("/graphql/graphiql.html")
    if (graphiqlFile.isReadable) {
      val template = graphiqlFile.inputStream.bufferedReader().use { it.readText() }

      val html = template
        .replace("\${graphqlEndpoint}", "/graphql")
        .replace("\${pageTitle}", "Reactive GraphQL example - GraphiQL")

      return InputStreamResource(html.byteInputStream())
    } else {
      throw HttpNotFoundException()
    }
  }

  fun executeGraphQLQuery(graphQLParameters: GraphQLParameters): Mono<ExecutionResult> {
    val executionInput = newExecutionInput()
      .query(graphQLParameters.query)
      .operationName(graphQLParameters.operationName)
      .variables(graphQLParameters.variables)

    val graphQL = GraphQL
      .newGraphQL(schema)
      .build()

    return fromFuture(graphQL.executeAsync(executionInput))
  }

  fun getGraphQLParameters(req: ServerRequest): Mono<GraphQLParameters> = when {
    req.queryParam("query").isPresent -> graphQLParametersFromRequestParameters(req)
    req.method() == HttpMethod.POST -> parsePostRequest(req)
    else -> empty()
  }

  fun parsePostRequest(req: ServerRequest) = when {
    req.contentTypeIs(graphQLMediaType) -> req.withBody { GraphQLParameters(query = it) }
    else -> req.withBody { readJson<GraphQLParameters>(it) }
  }

  fun graphQLParametersFromRequestParameters(req: ServerRequest) =
    just(
      GraphQLParameters(
        query = req.queryParam("query").get(),
        operationName = req.queryParam("operationName").orElseGet { null },
        variables = getVariables(req)
      )
    )

  fun getVariables(req: ServerRequest): Map<String, Any>? {
    return req.queryParam("variables")
      .map { URLDecoder.decode(it, "UTF-8") }
      .map { readJsonMap(it) }
      .orElseGet { null }
  }

  val mapTypeRef: MapType =
    TypeFactory.defaultInstance().constructMapType(HashMap::class.java, String::class.java, Any::class.java)

  fun ServerRequest.contentTypeIs(mediaType: MediaType) =
    this.headers().contentType().filter { it.isCompatibleWith(mediaType) }.isPresent

  fun <T> ServerRequest.withBody(mapFun: (String) -> T): Mono<T> =
    this.bodyToMono<String>().flatMap { Mono.just(mapFun(it)) }

  private inline fun <reified T> readJson(value: String): T = jacksonObjectMapper().readValue(value, T::class.java)

  fun readJsonMap(variables: String?): Map<String, Any>? = jacksonObjectMapper().readValue(variables, mapTypeRef)

  fun serveStatic(resource: Resource, contentType: MediaType = MediaType.ALL): (ServerRequest) -> Mono<ServerResponse> =
    {
      if (contentType.equals(MediaType.ALL)) {
        ServerResponse.ok().body(BodyInserters.fromResource(resource))
      } else {
        ServerResponse.ok().contentType(contentType).body(BodyInserters.fromResource(resource))
      }
    }
}

data class GraphQLParameters(
  val query: String,
  val operationName: String? = null,
  val variables: Map<String, Any>? = null
)
