package com.hancook.hancookbe.dtos

data class ResponseProfit(
    val revenues: DataByTime,
    val expenses: DataByTime
)

data class ResponseTop5Dish(
    val dishes: List<ResponseDishCountDto>,
    val numberOfInvoices: Long
)

data class ResponseTableState(
    val occupied: Int,
    val available: Int
)

data class DataByTime(
    val times: List<String>,
    val data: List<Long>,
    val total: Long
)