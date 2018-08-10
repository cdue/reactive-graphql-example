package com.cdue.reactivegraphqlexample.graphql.resolver

import com.cdue.reactivegraphqlexample.model.Stock
import com.cdue.reactivegraphqlexample.model.StockQuote
import com.cdue.reactivegraphqlexample.repository.StockQuoteRepository
import com.cdue.reactivegraphqlexample.repository.StockRepository
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class Query(private val stockQuoteRepository: StockQuoteRepository,
            private val stockRepository: StockRepository) : GraphQLQueryResolver {

    fun stocks(): List<Stock> {
        return stockRepository.findAll()
    }

    fun stock(code: String): Stock? {
        return stockRepository.findOne(code)
    }

    fun stockQuotes(stockCode: String): List<StockQuote> {
        return stockQuoteRepository.findByStockCode(stockCode)
    }

    fun stockPrice(stockCode: String): Float? {
        return stockQuoteRepository.findLastByStockCode(stockCode)?.price
    }
}