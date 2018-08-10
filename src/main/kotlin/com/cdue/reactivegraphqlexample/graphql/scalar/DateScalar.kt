package com.cdue.reactivegraphqlexample.graphql.scalar

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Component
class DateScalar : GraphQLScalarType("DateTime", "DateTime", object : Coercing<ZonedDateTime, String> {
    override fun serialize(dataFetcherResult: Any): String {
        if (dataFetcherResult is ZonedDateTime) {
            return dataFetcherResult.format(DateTimeFormatter.ISO_DATE_TIME)
        } else if (dataFetcherResult is String) {
            val dateString = ZonedDateTime.parse(dataFetcherResult, DateTimeFormatter.ISO_DATE_TIME)
            return dateString.format(DateTimeFormatter.ISO_DATE_TIME)
        }
        throw CoercingSerializeException("Invalid value $dataFetcherResult for ZonedDateTime")
    }

    override fun parseValue(input: Any): ZonedDateTime {
        if (input is String) {
            return ZonedDateTime.parse(input, DateTimeFormatter.ISO_DATE_TIME)
        }

        throw CoercingSerializeException("Invalid value $input for ZonedDateTime")
    }

    override fun parseLiteral(input: Any): ZonedDateTime? {
        if (input !is StringValue) return null
        val value = input.value
        return ZonedDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
    }
})
