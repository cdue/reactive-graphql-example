package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.model.StockIndex
import com.cdue.reactivegraphqlexample.model.StockIndexValue
import com.cdue.reactivegraphqlexample.repository.StockIndexRepository
import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class StockIndexResolver(private val indexRepository: StockIndexRepository) : GraphQLResolver<StockIndex> {

    fun values(stockIndex: StockIndex, from: ZonedDateTime?, to: ZonedDateTime?, limit: Int?): List<StockIndexValue> {

        return emptyList()
    }
}
