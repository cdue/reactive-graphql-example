package com.cdue.reactivegraphqlexample.model

data class StockIndex(
        val code: String,
        val name: String,
        val currentValue: StockIndexValue
)