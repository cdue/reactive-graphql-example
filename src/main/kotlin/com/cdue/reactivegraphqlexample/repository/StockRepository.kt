package com.cdue.reactivegraphqlexample.repository

import com.cdue.reactivegraphqlexample.model.Stock
import org.springframework.stereotype.Service

@Service
class StockRepository {

  fun findAll(): List<Stock> {
    return stocks
  }

  fun findOne(code: String): Stock? {
    return stocks.first { it.code == code }
  }

  companion object {
    val stocks = listOf(Stock("DJIA", "Dow Jones Industrial Average"), Stock("COMP", "NASDAQ Composite Index"))
  }
}
