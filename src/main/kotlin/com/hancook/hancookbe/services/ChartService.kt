package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toCount
import com.hancook.hancookbe.converters.toDataByTime
import com.hancook.hancookbe.converters.toResponse
import com.hancook.hancookbe.dtos.*
import com.hancook.hancookbe.models.Dish
import com.hancook.hancookbe.repositories.ExpenseRepository
import com.hancook.hancookbe.repositories.InvoiceDetailRepository
import com.hancook.hancookbe.repositories.InvoiceRepository
import com.hancook.hancookbe.repositories.TableRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters

@Service
@Transactional
class ChartService(
    @Autowired private val invoiceRepository: InvoiceRepository,
    @Autowired private val expenseRepository: ExpenseRepository,
    @Autowired private val tableRepository: TableRepository,
    @Autowired private val invoiceDetailRepository: InvoiceDetailRepository
) {
    fun calculateProfits(timeRange: String, startDate: LocalDate?, endDate: LocalDate?): ResponseProfit{
        return when(timeRange){
            "today" -> this.calculateProfitsForDate(LocalDateTime.now())
            "yesterday" -> this.calculateProfitsForDate(LocalDateTime.now().minusDays(1))
            "week" -> this.calculateProfitsForWeek()
            "month" -> this.calculateProfitsForMonth()
            "quarter" -> this.calculateProfitsForQuarter()
            "year" -> this.calculateProfitsForYear()
            else ->  throw IllegalArgumentException("Invalid time range")
        }
    }

    fun countTop5DishesByCheckoutDetails(): ResponseTop5Dish {
        val dishes = mutableListOf<ResponseDishCountDto>()
        val counts = mutableListOf<Long>()

        val dishCountList = invoiceDetailRepository.countTop5DishesByCheckoutDetails().take(5)

        for (entry in dishCountList) {
            val dish = entry[0] as Dish
            val count = entry[1] as Long
            dishes.add(dish.toCount(count))
        }

        return ResponseTop5Dish(dishes, invoiceRepository.count())
    }


    fun calculateTop5Dishes(timeRange: String): ResponseTop5Dish {
        val startDate: LocalDateTime
        val endDate: LocalDateTime

        when (timeRange) {
            "today" -> {
                startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
                endDate = startDate.plusDays(1)
            }
            "yesterday" -> {
                startDate = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
                endDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
            }
            "week" -> {
                startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays((LocalDateTime.now().dayOfWeek.value - 1).toLong())
                endDate = startDate.plusWeeks(1)
            }
            "month" -> {
                startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).withDayOfMonth(1)
                endDate = startDate.plusMonths(1)
            }
            "quarter" -> {
                startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).withMonth(((LocalDateTime.now().monthValue - 1) / 3) * 3 + 1).withDayOfMonth(1)
                endDate = startDate.plusMonths(3)
            }
            "year" -> {
                startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).withDayOfYear(1)
                endDate = startDate.plusYears(1)
            }
            else -> throw IllegalArgumentException("Invalid time range")
        }

        val dishes = mutableListOf<ResponseDishCountDto>()
        val dishCountList = invoiceDetailRepository.countTop5DishesByCheckoutDetails(startDate, endDate).take(5)

        for (entry in dishCountList) {
            val dish = entry[0] as Dish
            val count = entry[1] as Long
            dishes.add(dish.toCount(count))
        }

        return ResponseTop5Dish(dishes, invoiceRepository.countByCreatedTimeBetween(startDate, endDate))
    }

    fun getTableStateCounts(): ResponseTableState {
        val occupiedCount = tableRepository.countByCustomerOrderIsNotNull()
        val availableCount = tableRepository.countByCustomerOrderIsNull()

        return ResponseTableState(occupiedCount.toInt(), availableCount.toInt())
    }

    fun calculateProfitsForDate(date: LocalDateTime): ResponseProfit {
        val today = date.with(LocalTime.MIN)
        val times = mutableListOf<LocalDateTime>()

        var startHour = today

        repeat(8){
            times.add(startHour)
            startHour = startHour.plusHours(4)
        }

        return calculateProfitsForTimeIntervals(times){
            it.hour.toString()
        }
    }

    fun calculateProfitsForWeek(): ResponseProfit {
        val currentDateTime = LocalDateTime.now()
        val currentDayOfWeek = currentDateTime.dayOfWeek.value
        val startOfCurrentWeek = currentDateTime.minusDays((currentDayOfWeek - 1).toLong()).toLocalDate()

        val times = (0 .. 7).map { startOfCurrentWeek.plusDays(it.toLong()).atStartOfDay() }

        return calculateProfitsForTimeIntervals(times)
    }

    fun calculateProfitsForMonth(): ResponseProfit {
        val currentDate = LocalDateTime.now().with(LocalTime.MIN)
        val firstDayOfCurrentMonth = currentDate.withDayOfMonth(1)
        val firstDayOfNextMonth = firstDayOfCurrentMonth.plusMonths(1)

        val firstMondayOfLastMonth = firstDayOfCurrentMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
        val weeks = (firstMondayOfLastMonth.until(firstDayOfCurrentMonth.with(TemporalAdjusters.lastDayOfMonth()), java.time.temporal.ChronoUnit.WEEKS) + 1).toInt()

        val times = mutableListOf<LocalDateTime>()

        if(firstDayOfCurrentMonth != firstMondayOfLastMonth){
            times.add(firstDayOfCurrentMonth)
        }

        var nextMonday = firstMondayOfLastMonth
        repeat(weeks) {
            times.add(nextMonday)
            nextMonday = nextMonday.plusWeeks(1)
        }
        times.add(firstDayOfCurrentMonth.with(TemporalAdjusters.lastDayOfMonth()))
        times.add(firstDayOfNextMonth)
        return calculateProfitsForTimeIntervals(times)
    }

    fun calculateProfitsForQuarter(): ResponseProfit {
        val currentDate = LocalDateTime.now().with(LocalTime.MIN)
        val currentMonth = currentDate.monthValue
        val currentQuarter = (currentMonth - 1) / 3 + 1
        val firstMonthOfQuarter = (currentQuarter - 1) * 3 + 1

        val firstDayOfCurrentQuarter = currentDate.withMonth(firstMonthOfQuarter).withDayOfMonth(1)

        val times = mutableListOf<LocalDateTime>()

        var startDate = firstDayOfCurrentQuarter
        repeat(4) {
            times.add(startDate)
            startDate = startDate.plusMonths(1)
        }

        return calculateProfitsForTimeIntervals(times)
    }

    fun calculateProfitsForYear(): ResponseProfit {
        val currentDate = LocalDateTime.now().with(LocalTime.MIN)

        val firstDayOfCurrentYear = currentDate.withMonth(1).withDayOfMonth(1)
        val times = mutableListOf<LocalDateTime>()

        var startDate = firstDayOfCurrentYear
        repeat(13) {
            times.add(startDate)
            startDate = startDate.plusMonths(1)
        }

        return calculateProfitsForTimeIntervals(times)
    }

    fun calculateProfitsForTimeIntervals(
        timeIntervals: List<LocalDateTime>,
        formatTime: (date: LocalDateTime) -> String = { dateTime -> dateTime.toString() }
    ) : ResponseProfit {
        val times = timeIntervals.dropLast(1)
        val revenueData = calculateRevenueForTimeIntervals(timeIntervals).toDataByTime(times)
        val expenseData = calculateExpensesForTimeIntervals(timeIntervals).toDataByTime(times)
        return ResponseProfit(revenueData, expenseData)
    }

    fun calculateRevenueForTimeIntervals(timeIntervals: List<LocalDateTime>): Map<String, Long> {
        val revenueMap = mutableMapOf<String, Long>()
        for (i in 0 until timeIntervals.size - 1) {
            val startDate = timeIntervals[i]
            val endDate = timeIntervals[i + 1].minusNanos(1) // Adjusted to the end of the previous interval
            val invoices = invoiceRepository.findAllByCreatedTimeBetween(startDate, endDate)
            val totalRevenue = invoices.sumOf { it.calculateTotalPrice() }
            revenueMap[startDate.toString()] = totalRevenue
        }
        return revenueMap
    }

    fun calculateExpensesForTimeIntervals(timeIntervals: List<LocalDateTime>): Map<String, Long> {
        val expenseMap = mutableMapOf<String, Long>()
        for (i in 0 until timeIntervals.size - 1) {
            val startDate = timeIntervals[i]
            val endDate = timeIntervals[i + 1].minusNanos(1) // Adjusted to the end of the previous interval
            val expenses = expenseRepository.findAllByDateTimeBetween(startDate, endDate)
            val totalExpense = expenses.sumOf { it.amount }
            expenseMap[startDate.toString()] = totalExpense
        }
        return expenseMap
    }
}