package openmeteo

import hu.vanio.kotlin.feladat.ms.openmeteo.Hourly
import hu.vanio.kotlin.feladat.ms.openmeteo.HourlyUnits
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherForecast
import hu.vanio.kotlin.feladat.ms.openmeteo.WeatherService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import java.time.LocalDate
import java.time.Month
import kotlin.test.assertEquals

class WeatherServiceTest {
    private val weatherForecastResponse = WeatherForecast(47.5,
            19.0625,
            0.030040740966796875,
            3600,
            "Europe/Budapest",
            "CET",
            124.0,
            hourlyUnits = HourlyUnits("iso8601", "°C"),
            hourly = Hourly(time = listOf("2024-03-18T00:00",
                "2024-03-18T01:00",
                "2024-03-18T02:00",
                "2024-03-18T03:00",
                "2024-03-18T04:00",
                "2024-03-18T05:00",
                "2024-03-18T06:00",
                "2024-03-18T07:00",
                "2024-03-18T08:00",
                "2024-03-18T09:00",
                "2024-03-18T10:00",
                "2024-03-18T11:00",
                "2024-03-18T12:00",
                "2024-03-18T13:00",
                "2024-03-18T14:00",
                "2024-03-18T15:00",
                "2024-03-18T16:00",
                "2024-03-18T17:00",
                "2024-03-18T18:00",
                "2024-03-18T19:00",
                "2024-03-18T20:00",
                "2024-03-18T21:00",
                "2024-03-18T22:00",
                "2024-03-18T23:00",
                "2024-03-19T00:00",
                "2024-03-19T01:00",
                "2024-03-19T02:00",
                "2024-03-19T03:00",
                "2024-03-19T04:00",
                "2024-03-19T05:00",
                "2024-03-19T06:00",
                "2024-03-19T07:00",
                "2024-03-19T08:00",
                "2024-03-19T09:00",
                "2024-03-19T10:00",
                "2024-03-19T11:00",
                "2024-03-19T12:00",
                "2024-03-19T13:00",
                "2024-03-19T14:00",
                "2024-03-19T15:00",
                "2024-03-19T16:00",
                "2024-03-19T17:00",
                "2024-03-19T18:00",
                "2024-03-19T19:00",
                "2024-03-19T20:00",
                "2024-03-19T21:00",
                "2024-03-19T22:00",
                "2024-03-19T23:00"),
                    temperature2m = listOf(6.0,
                            5.7,
                            5.4,
                            5.2,
                            4.8,
                            4.6,
                            4.5,
                            4.7,
                            5.9,
                            7.3,
                            8.9,
                            9.7,
                            10.4,
                            10.9,
                            11.1,
                            11.0,
                            10.6,
                            9.8,
                            8.7,
                            7.4,
                            6.2,
                            5.4,
                            4.7,
                            4.2,
                            3.7,
                            3.3,
                            3.0,
                            2.9,
                            2.8,
                            2.8,
                            2.8,
                            3.2,
                            3.9,
                            4.7,
                            5.5,
                            6.4,
                            7.4,
                            8.0,
                            8.5,
                            8.8,
                            8.8,
                            8.6,
                            7.8,
                            6.7,
                            5.6,
                            4.7,
                            4.0,
                            3.4))
            )

    @Test
    fun statistics() {
        val weatherForecasts = WeatherService.statistics(weatherForecastResponse)

        Assertions.assertAll (
            Executable { assertEquals(2, weatherForecasts.weatherDailyForecast.size) } ,
            Executable { assertEquals(LocalDate.of(2024, Month.MARCH, 18), weatherForecasts.weatherDailyForecast[0].day) },
            Executable { assertEquals(7.21, weatherForecasts.weatherDailyForecast[0].average()) },
            Executable { assertEquals(LocalDate.of(2024, Month.MARCH, 19), weatherForecasts.weatherDailyForecast[1].day) },
            Executable { assertEquals(5.3, weatherForecasts.weatherDailyForecast[1].average()) }
        )
    }
}