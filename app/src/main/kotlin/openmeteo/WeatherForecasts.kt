package hu.vanio.kotlin.feladat.ms.openmeteo

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

data class WeatherDailyForecast(val day: LocalDate, private val temperatures: List<Double>) {
    fun average(): Double = temperatures.average().round(2)

    override fun toString(): String {
        return "$day - ${average()}"
    }
}

fun Double.round(digits: Int) =
    BigDecimal(this).setScale(digits, RoundingMode.HALF_EVEN).toDouble()

data class WeatherForecasts(val weatherDailyForecast: List<WeatherDailyForecast>)