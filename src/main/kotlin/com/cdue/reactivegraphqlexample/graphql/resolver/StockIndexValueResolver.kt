package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.model.StockIndex
import com.cdue.reactivegraphqlexample.model.StockIndexValue
import com.cdue.reactivegraphqlexample.repository.StockIndexRepository
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component

@Component
class StockIndexValueResolver(private val indexRepository: StockIndexRepository) : GraphQLResolver<StockIndexValue> {

    fun index(stockIndexValue: StockIndexValue): StockIndex {
        return indexRepository.getStockIndex(stockIndexValue.indexCode)!!
    }
}
