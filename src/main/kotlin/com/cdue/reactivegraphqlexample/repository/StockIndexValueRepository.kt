package com.cdue.reactivegraphqlexample.repository

import com.cdue.reactivegraphqlexample.model.StockIndex
import com.cdue.reactivegraphqlexample.model.StockIndexValue
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class StockIndexValueRepository {

    fun getStockIndexes(): List<StockIndex> {
        return stockIndexesMap.values.toList()
    }

    fun getStockIndex(code: String): StockIndex? {
        if(stockIndexesMap.containsKey(code)) {
            return stockIndexesMap[code]
        }

        return null
    }

    companion object {
        var stockIndexValues = mapOf("DJIA" to listOf(StockIndexValue("DJIA", 25509.23f, ZonedDateTime.now())))

        var stockIndexesMap = mapOf(
                "DJIA" to StockIndex("DJIA", "Dow Jones Industrial Average",
                        StockIndexValue("DJIA", 25509.23f, ZonedDateTime.now())),
                "COMP" to StockIndex("COMP", "NASDAQ Composite Index",
                        StockIndexValue("COMP", 7891.78f, ZonedDateTime.now())))
    }
}