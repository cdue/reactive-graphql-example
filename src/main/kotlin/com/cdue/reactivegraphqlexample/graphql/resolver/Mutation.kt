package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.graphql.input.StockQuoteInput
import com.cdue.reactivegraphqlexample.model.StockQuote
import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class Mutation : GraphQLMutationResolver {
    fun addStockQuote(stockQuote: StockQuoteInput): StockQuote? = null
}
