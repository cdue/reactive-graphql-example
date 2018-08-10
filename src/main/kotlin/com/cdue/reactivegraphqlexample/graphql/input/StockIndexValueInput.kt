package com.cdue.reactivegraphqlexample.graphql.input

import java.time.ZonedDateTime

data class StockIndexValueInput(
        val indexCode: String = "UNKNOWN",
        val value: Float = 0.0f,
        val date: ZonedDateTime = ZonedDateTime.now()
)