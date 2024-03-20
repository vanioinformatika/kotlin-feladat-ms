package hu.vanio.kotlin.feladat.ms

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL
import java.time.LocalDate
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.IOException

@SpringBootApplication
class WeatherApp

fun main() {
    runApplication<WeatherApp>()
    val url = "https://api.open-meteo.com/v1/forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto"

    try {
        val json = URL(url).readText()
        val forecast = jacksonObjectMapper().readValue<Forecast>(json)

        val dailyAverages = averageTempreature(forecast)
        printForecast(dailyAverages)

    } catch (e: IOException) {
        println("Adatok letöltése sikertelen: ${e.message}")
    } catch (e: JsonProcessingException) {
        println("JSON feldolgozása sikertelen: ${e.message}")
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Forecast(val hourly: HourlyData)
//@JsonIgnoreProperties(ignoreUnknown = true)
data class HourlyData(val time: List<String>, val temperature_2m: List<Double>)

fun averageTempreature(forecast: Forecast): Map<LocalDate, Double> {
    val dailyTemperatures = mutableMapOf<LocalDate, MutableList<Double>>()

    forecast.hourly.time.forEachIndexed { index, timestamp ->
        val date = LocalDate.parse(timestamp.substring(0, 10))
        val temperature = forecast.hourly.temperature_2m[index]

        if (dailyTemperatures.containsKey(date)) {
            dailyTemperatures[date]!!.add(temperature)
        } else {
            dailyTemperatures[date] = mutableListOf(temperature)
        }
    }
    return dailyTemperatures.mapValues { it.value.average() }
}

fun printForecast(dailyAverages: Map<LocalDate, Double>) {
    dailyAverages.forEach { (date, temperature) ->
        println("$date: $temperature °C")
    }
}