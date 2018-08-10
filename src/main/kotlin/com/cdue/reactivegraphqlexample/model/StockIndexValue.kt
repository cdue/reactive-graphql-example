package com.cdue.reactivegraphqlexample.model

import java.time.ZonedDateTime

data class StockIndexValue (
        val indexCode: String,
        val value: Float,
        val date: ZonedDateTime
)