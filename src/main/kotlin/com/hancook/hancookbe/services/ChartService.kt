package com.hancook.hancookbe.services

import com.hancook.hancookbe.converters.toDataByTime
import com.hancook.hancookbe.dtos.ResponseProfit
import com.hancook.hancookbe.repositories.ExpenseRepository
import com.hancook.hancookbe.repositories.InvoiceRepository
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
    @Autowired private val expenseRepository: ExpenseRepository
) {
    fun calculateProfits(timeRange: String, startDate: LocalDate?, endDate: LocalDate?): ResponseProfit{
        return  when(timeRange){
            "today" -> this.calculateProfitsForDate(LocalDate.now())
            "yesterday" -> this.calculateProfitsForDate(LocalDate.now().minusDays(1))
            "week" -> this.calculateProfitsForWeek()
            "month" -> this.calculateProfitsForMonth()
            "quarter" -> this.calculateProfitsForQuarter()
            else ->  throw IllegalArgumentException("Invalid time range")
        }
    }

    fun calculateProfitsForDate(date: LocalDate): ResponseProfit {
        val times = listOf("0", "4", "8", "12", "16", "20", "24")
        val revenueData = calculateRevenueForDateByInterval(date).toDataByTime(times)
        val expenseData = calculateExpensesForDateByInterval(date).toDataByTime(times)

        return ResponseProfit(revenueData, expenseData)
    }

    fun calculateProfitsForWeek(): ResponseProfit {
        val currentDateTime = LocalDateTime.now()
        val currentDayOfWeek = currentDateTime.dayOfWeek.value
        val startOfCurrentWeek = currentDateTime.minusDays((currentDayOfWeek - 1).toLong()).toLocalDate()

        val startOfLastWeek = startOfCurrentWeek.minusWeeks(1)

        val times = (0 .. 7).map { startOfLastWeek.plusDays(it.toLong()).atStartOfDay() }

        return calculateProfitsForTimeIntervals(times)
    }

    fun calculateProfitsForMonth(): ResponseProfit {
        val currentDate = LocalDateTime.now().with(LocalTime.MIN)
        val firstDayOfCurrentMonth = currentDate.withDayOfMonth(1)
        val firstDayOfLastMonth = firstDayOfCurrentMonth.minusMonths(1)

        val firstMondayOfLastMonth = firstDayOfLastMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
        val weeks = (firstMondayOfLastMonth.until(firstDayOfLastMonth.with(TemporalAdjusters.lastDayOfMonth()), java.time.temporal.ChronoUnit.WEEKS) + 1).toInt()

        val times = mutableListOf<LocalDateTime>()

        if(firstDayOfLastMonth != firstMondayOfLastMonth){
            times.add(firstDayOfLastMonth)
        }

        var nextMonday = firstMondayOfLastMonth
        repeat(weeks) {
            times.add(nextMonday)
            nextMonday = nextMonday.plusWeeks(1)
        }
        times.add(firstDayOfLastMonth.with(TemporalAdjusters.lastDayOfMonth()))
        times.add(firstDayOfCurrentMonth)
        return calculateProfitsForTimeIntervals(times)
    }

    fun calculateProfitsForQuarter(): ResponseProfit {
        val currentDate = LocalDateTime.now().with(LocalTime.MIN)
        val currentMonth = currentDate.monthValue
        val currentQuarter = (currentMonth - 1) / 3 + 1
        val firstMonthOfQuarter = (currentQuarter - 1) * 3 + 1

        val firstDayOfLastQuarter = currentDate.withMonth(firstMonthOfQuarter - 3).withDayOfMonth(1)
        val startOfFirstMonthOfLastQuarter = firstDayOfLastQuarter.withDayOfMonth(1)

        val times = mutableListOf<LocalDateTime>()

        var startDate = startOfFirstMonthOfLastQuarter
        repeat(4) {
            times.add(startDate)
            startDate = startDate.plusMonths(1)
        }

        return calculateProfitsForTimeIntervals(times) {
            date: LocalDateTime -> "${date.month}"
        }
    }

    fun calculateProfitsForTimeIntervals(
        timeIntervals: List<LocalDateTime>,
        formatTime: (date: LocalDateTime) -> String = { dateTime -> dateTime.toString() }
    ) : ResponseProfit {
        val times = timeIntervals.map { formatTime(it) }.dropLast(1)
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
            val totalRevenue = invoices.sumOf { it.customerPayment }
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

    private fun calculateRevenueForDateByInterval(date: LocalDate): Map<String, Long> {
        val invoices = invoiceRepository.findAllByCreatedTimeBetween(
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        )
        return invoices.groupBy { it.createdTime.hour / 4 }
            .mapValues { it.value.sumOf { invoice -> invoice.customerPayment } }
            .mapKeys { entry -> "${entry.key * 4}" }
    }

    private fun calculateExpensesForDateByInterval(date: LocalDate): Map<String, Long> {
        val expenses = expenseRepository.findAllByDateTimeBetween(
            date.atStartOfDay(),
            date.atTime(LocalTime.MAX)
        )
        return expenses.groupBy { it.dateTime.hour / 4 }
            .mapValues { it.value.sumOf { expense -> expense.amount } }
            .mapKeys { entry -> "${entry.key * 4}" }
    }
}