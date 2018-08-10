package com.cdue.reactivegraphqlexample.graphql.config

import com.cdue.reactivegraphqlexample.graphql.resolver.Mutation
import com.cdue.reactivegraphqlexample.graphql.resolver.Query
import com.cdue.reactivegraphqlexample.graphql.resolver.StockIndexResolver
import com.cdue.reactivegraphqlexample.graphql.resolver.StockIndexValueResolver
import com.cdue.reactivegraphqlexample.graphql.scalar.DateScalar
import com.coxautodev.graphql.tools.SchemaParser
import graphql.schema.GraphQLSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class used by graphql-java-tools to load the GraphQL Schema and its related resolvers
 */
@Configuration
class SchemaConfiguration(
        private val query: Query,
        private val mutation: Mutation,
        private val stockIndexResolver: StockIndexResolver,
        private val stockIndexValueResolver: StockIndexValueResolver,
        private val dateScalar: DateScalar) {

    @Bean
    fun loadSchema(): GraphQLSchema {
        return SchemaParser.newParser()
                .file("graphql/schema.graphqls")
                .scalars(dateScalar)
                .resolvers(query, mutation, stockIndexResolver, stockIndexValueResolver)
                .build()
                .makeExecutableSchema()
    }
}
