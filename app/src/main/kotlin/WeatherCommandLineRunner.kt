package hu.vanio.kotlin.feladat.ms

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
        print("$separator\n${weatherService.getWeatherStatistics().print()}\n$separator\n")
    }
}

fun WeatherForecasts.print() =
        "Weather forecast daily averages:\n$separator\n${this.weatherDailyForecast
                .map { wdf -> "${wdf.day} | ${wdf.average()}"}
                .reduce { first, second -> "${first}\n${second}"}}"