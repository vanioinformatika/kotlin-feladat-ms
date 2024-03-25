package hu.vanio.kotlin.feladat.ms

import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class WeatherAppTest {

    @Test
    fun `sikeres lekerdezes`() {
        val forecast = Forecast(
            HourlyData(
                listOf(
                    "2024-03-18T00:00", "2024-03-18T01:00", "2024-03-18T02:00",
                    "2024-03-19T00:00", "2024-03-19T01:00", "2024-03-19T02:00",
                    "2024-03-20T00:00", "2024-03-20T01:00", "2024-03-20T02:00"
                ),
                listOf(
                    6.0, 5.7, 5.4, 5.2, 4.3, 4.6, 6.5, 7.7, 8.9
                )
            )
        )
        val result = averageTempreature(forecast)

        val expected = mapOf(
            LocalDate.parse("2024-03-18") to 5.7,
            LocalDate.parse("2024-03-19") to 4.7,
            LocalDate.parse("2024-03-20") to 7.7
        )
        assertEquals(expected, result)
    }

    @Test
    fun `sikertelen lekerdezes`() {
        val forecast = Forecast(
            HourlyData(
                listOf("2024-03-18T00:00", "2024-03-18T01:00", "2024-03-18T02:00",
                    "2024-03-19T00:00", "2024-03-19T01:00", "2024-03-19T02:00",
                    "2024-03-20T00:00", "2024-03-20T01:00", "2024-03-20T02:00"
                ),
                listOf(
                    6.0, 5.7, 5.4, 5.2, 4.3, 4.6, 6.5, 7.7, 11.1
                )
            )
        )
        val result = averageTempreature(forecast)

        val expected = mapOf(
            LocalDate.parse("2024-03-18") to 5.7,
            LocalDate.parse("2024-03-19") to 4.7,
            LocalDate.parse("2024-03-20") to 7.4
        )
        //3.nap 8.43 
        assertNotEquals(expected, result);
    }
}