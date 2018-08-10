package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.graphql.input.StockIndexValueInput
import com.cdue.reactivegraphqlexample.model.StockIndexValue
import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component

@Component
class Mutation : GraphQLMutationResolver {
    fun addStockIndexValue(stockIndexValue: StockIndexValueInput): StockIndexValue? = null
}
