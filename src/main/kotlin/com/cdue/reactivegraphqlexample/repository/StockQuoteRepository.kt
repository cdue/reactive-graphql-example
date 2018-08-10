package com.cdue.reactivegraphqlexample.repository

import com.cdue.reactivegraphqlexample.model.Stock
import com.cdue.reactivegraphqlexample.model.StockQuote
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class StockQuoteRepository {

    fun findByStockCode(stockCode: String): List<StockQuote> {
        if(stockQuotesByStockCode.containsKey(stockCode)) {
            return stockQuotesByStockCode[stockCode]!!
        }

        return emptyList()
    }

    fun findLastByStockCode(stockCode: String): StockQuote? {
        if(stockQuotesByStockCode.containsKey(stockCode)) {
            return stockQuotesByStockCode[stockCode]!!.sortedBy { it.date }.last()
        }

        return null
    }

    companion object {
        private val now = ZonedDateTime.now()

        var stockQuotesByStockCode = mapOf(
                "DJIA" to listOf(
                        StockQuote("DJIA", now.minusMinutes(10), 25509.23f, -0.4f),
                        StockQuote("DJIA", now.minusMinutes(5), 25509.43f, 0.2f),
                        StockQuote("DJIA", now, 25509.44f, 0.01f)),
                "COMP" to listOf(
                        StockQuote("COMP", now.minusMinutes(10), 7891.78f, 0.11f),
                        StockQuote("COMP", now.minusMinutes(5), 7891.84f, 0.06f),
                        StockQuote("COMP", now, 7891.82f, -0.2f)))
    }
}