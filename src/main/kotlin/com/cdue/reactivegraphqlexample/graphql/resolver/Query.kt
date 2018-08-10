package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.model.StockIndex
import com.cdue.reactivegraphqlexample.repository.StockIndexRepository
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class Query(private val stockIndexRepository: StockIndexRepository) : GraphQLQueryResolver {

    fun getStockIndexes(): List<StockIndex> = stockIndexRepository.getStockIndexes()
    fun stockIndex(code: String): StockIndex? = stockIndexRepository.getStockIndex(code)
}