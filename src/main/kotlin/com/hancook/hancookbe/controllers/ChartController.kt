package com.hancook.hancookbe.controllers

import com.hancook.hancookbe.dtos.ResponseTop5Dish
import com.hancook.hancookbe.dtos.ResponseProfit
import com.hancook.hancookbe.dtos.ResponseTableState
import com.hancook.hancookbe.services.ChartService
import com.hancook.hancookbe.system.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("\${api.endpoint.base-url}/charts")
class ChartController(
    @Autowired private val chartService: ChartService
) {
    @GetMapping("/profits", "/profits/")
    fun getProfits(
        @RequestParam timeRange: String,
        @RequestParam(required = false) startDate: LocalDate?,
        @RequestParam(required = false) endDate: LocalDate?
    ): ResponseEntity<ApiResponse<ResponseProfit>> {
        val profits = chartService.calculateProfits(timeRange, startDate, endDate)
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = profits,
                message = "Found profits"
            )
        )
    }

    @GetMapping("/tables", "/tables/")
    fun getTables(
    ): ResponseEntity<ApiResponse<ResponseTableState>> {
        val tableStateCount = chartService.getTableStateCounts()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = tableStateCount,
                message = "Found number of tables by states"
            )
        )
    }

    @GetMapping("/dishes", "/dishes/")
    fun getDishes(
    ): ResponseEntity<ApiResponse<ResponseTop5Dish>> {
        val dishes = chartService.countTop5DishesByCheckoutDetails()
        return ResponseEntity.ok(
            ApiResponse(
                success = true,
                statusCode = HttpStatus.OK.value(),
                data = dishes,
                message = "Found top 5 dishes"
            )
        )
    }
}