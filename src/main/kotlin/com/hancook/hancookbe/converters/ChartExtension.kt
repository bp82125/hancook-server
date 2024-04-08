package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.DataByTime

fun Map<String, Long>.toDataByTime(times: List<String>): DataByTime {
    val dataValues = times.map { this[it] ?: 0L }
    return DataByTime(times, dataValues, dataValues.sum())
}