package hu.vanio.kotlin.feladat.ms.openmeteo

import com.fasterxml.jackson.databind.ObjectMapper
import hu.vanio.kotlin.feladat.ms.WeatherAppConfig
import org.springframework.stereotype.Service

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class WeatherService(var config: WeatherAppConfig, var objectMapper: ObjectMapper) {
    fun getWeatherStatistics(): WeatherForecasts {
        val request = HttpRequest.newBuilder()
                .uri(URI(config.appUrl))
                .GET()
                .build()

        val weatherForecast = objectMapper.readValue(HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString()).body(), WeatherForecast::class.java)
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