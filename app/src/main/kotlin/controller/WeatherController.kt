package hu.vanio.kotlin.feladat.ms.controller

import hu.vanio.kotlin.feladat.ms.data.DailyAverageData
import hu.vanio.kotlin.feladat.ms.data.DailyTempDataContainer
import hu.vanio.kotlin.feladat.ms.service.WeatherService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherController(private val weatherService: WeatherService) {
    @GetMapping("/weather")
    suspend fun getWeeklyTempData(): ResponseEntity<DailyTempDataContainer> {
        return ResponseEntity.ok(weatherService.getDailyTempData())
    }

    @GetMapping("/average")
    suspend fun getAverageTemps(): ResponseEntity<List<DailyAverageData>> {
        return ResponseEntity.ok(weatherService.getDailyAverageTemp())
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: ${e.message}")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
    }
}