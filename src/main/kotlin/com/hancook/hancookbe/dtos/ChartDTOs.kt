package com.hancook.hancookbe.dtos

data class ResponseProfit(
    val revenues: DataByTime,
    val expenses: DataByTime
)

data class DataByTime(
    val times: List<String>,
    val data: List<Long>,
    val total: Long
)