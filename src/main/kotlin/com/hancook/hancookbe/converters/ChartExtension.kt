package com.hancook.hancookbe.converters

import com.hancook.hancookbe.dtos.DataByTime
import java.time.LocalDateTime

fun Map<String, Long>.toDataByTime(times: List<LocalDateTime>, formatTime: (date: LocalDateTime) -> String = { dateTime -> dateTime.toString() }): DataByTime {
    val dataValues = times.map { this[it.toString()] ?: 0L }
    val returnTimes = times.map { formatTime(it) }
    return DataByTime(returnTimes, dataValues, dataValues.sum())
}