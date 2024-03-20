package hu.vanio.kotlin.feladat.ms.openmeteo

import com.fasterxml.jackson.databind.ObjectMapper
import hu.vanio.kotlin.feladat.ms.WeatherAppConfig
import hu.vanio.kotlin.feladat.ms.exception.ServiceUnavailable
import org.springframework.stereotype.Service
import java.io.IOException

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class WeatherService(var config: WeatherAppConfig, var objectMapper: ObjectMapper) {
    private val id = "WEATHER_SERVICE"

    fun getWeatherStatistics(): WeatherForecasts {
        val request = HttpRequest.newBuilder()
                .uri(URI(config.appUrl))
                .GET()
                .build()
        val weatherForecast = try {objectMapper.readValue(HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString()).body(), WeatherForecast::class.java)
        } catch (e: IOException) {
            throw ServiceUnavailable(id)
        }
        return statistics(weatherForecast)
    }

    companion object {
        fun statistics(weatherForecast: WeatherForecast): WeatherForecasts {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            val weatherMap = mutableMapOf<LocalDate, MutableList<Double>>()
            var i = 0

            weatherForecast.hourly.time.forEach {
                val temperature = weatherForecast.hourly.temperature2m[i++]
                val key = LocalDate.parse(it, dateTimeFormatter)
                weatherMap.compute(key) { _, value ->
                    value?.plus(temperature)?.toMutableList() ?: mutableListOf(temperature)
                }
            }
            return WeatherForecasts(weatherMap.map {
                WeatherDailyForecast(it.key, it.value)
            }.toList())
        }
    }
}