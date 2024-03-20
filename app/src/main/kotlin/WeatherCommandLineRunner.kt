package hu.vanio.kotlin.feladat.ms

import hu.vanio.kotlin.feladat.ms.exception.ServiceUnavailable
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherForecasts
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherService
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


const val separator = "----------------------------------------------"

@Component
@Order(1)
class WeatherCommandLineRunner(val weatherService: WeatherService): CommandLineRunner {

    override fun run(vararg args: String?) {
        val weatherStatistics = try { weatherService.getWeatherStatistics() } catch (e: ServiceUnavailable) {
            println("Weather forecast service is unavailable")
            return
        }
        println("$separator\n${weatherStatistics.println()}$separator")
    }
}

fun WeatherForecasts.println() =
        "Weather forecast daily averages:\n$separator\n${this.weatherDailyForecast
                .map { wdf -> "${wdf.day} | ${wdf.average()}"}
                .reduce { first, second -> "${first}\n${second}"}}"