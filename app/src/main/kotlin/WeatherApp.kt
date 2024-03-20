package hu.vanio.kotlin.feladat.ms

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@SpringBootApplication
class WeatherApp

fun main() {
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://api.open-meteo.com/v1/forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto"))
        .build();

    val response = client.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() != 200) {
        throw DataUnavailableException("Weather datas are unavailable! Response was: " + response.body())
    }
    val mapper = jacksonObjectMapper()
    mapper.registerModule(JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    val weatherData: WeatherData = mapper.readValue(response.body())
    val times = weatherData.hourly.time.toList()
    val temperatures = weatherData.hourly.temperature_2m.toList()
    val dayWithTemperatures = mutableListOf<DayWithTemperature>()
    for (i in times.indices) {
        dayWithTemperatures.add(DayWithTemperature(times[i].dayOfMonth, temperatures[i].toDouble()))
    }

    val daysWithTemperaturesMap = mutableMapOf<Int, Double>()
    dayWithTemperatures.forEach {
        if (!daysWithTemperaturesMap.containsKey(it.day)) {
            daysWithTemperaturesMap[it.day] = it.temp
        } else {
            daysWithTemperaturesMap[it.day] = daysWithTemperaturesMap[it.day]!! + it.temp
        }
    }
    daysWithTemperaturesMap.forEach {
        println("${it.key}: ${it.value/24}")
    }
}

