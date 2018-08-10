package com.cdue.reactivegraphqlexample.model

import java.time.ZonedDateTime

data class StockQuote(
  val stockCode: String,
  val date: ZonedDateTime,
  val price: Float,
  val priceChange: Float
)
