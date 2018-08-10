package com.cdue.reactivegraphqlexample.graphql.input

import java.time.ZonedDateTime

data class StockQuoteInput(
  val stockCode: String,
  val price: Float,
  val date: ZonedDateTime? = ZonedDateTime.now()
)
