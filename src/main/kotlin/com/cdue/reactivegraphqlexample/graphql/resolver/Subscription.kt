package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.model.StockQuote
import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver
import org.springframework.stereotype.Component

@Component
class Subscription : GraphQLSubscriptionResolver {
    fun stockQuotes(stockCodes: List<String>?): StockQuote? = null
}
