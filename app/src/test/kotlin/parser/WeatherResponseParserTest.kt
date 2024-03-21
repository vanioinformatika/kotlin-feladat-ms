package parser

import hu.vanio.kotlin.feladat.ms.data.DailyTempData
import hu.vanio.kotlin.feladat.ms.data.HourlyData
import hu.vanio.kotlin.feladat.ms.data.WeeklyTempData
import hu.vanio.kotlin.feladat.ms.parser.WeatherResponseParser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class WeatherResponseParserTest {
    private val parser = WeatherResponseParser()

    @Test
    fun `test groupToWeekData with null hourly time`() {
        val hourlyData = HourlyData(null, temp())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            parser.groupToWeekData(hourlyData)
        }

        assertEquals("No time data available", exception.message)
    }

    @Test
    fun `test groupToWeekData with null hourly temp`() {
        val hourlyData = HourlyData(time(), null)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            parser.groupToWeekData(hourlyData)
        }

        assertEquals("No temp data available", exception.message)
    }

    @Test
    fun `test groupToWeekData when time data is empty`() {
        val hourlyData = HourlyData(emptyList(), temp())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            parser.groupToWeekData(hourlyData)
        }

        assertEquals("No time data available", exception.message)
    }

    @Test
    fun `test groupToWeekData when temp data is empty`() {
        val hourlyData = HourlyData(time(), emptyList())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            parser.groupToWeekData(hourlyData)
        }

        assertEquals("No temp data available", exception.message)
    }

    @Test
    fun `test groupToWeekData with valid data`() {
        val hourlyData = HourlyData(time(), temp())

        val result = parser.groupToWeekData(hourlyData)

        assertEquals(WeeklyTempData::class.java, result.javaClass)
        assertEquals(LocalDate.parse("2024-03-19"), result.from)
        assertEquals(LocalDate.parse("2024-03-20"), result.to)
        assertEquals(2, result.dailyTempData.size)

        assertEquals(expectedFirstDayData().hourlyTempData, result.dailyTempData[0].hourlyTempData)
        assertEquals(expectedSecondDayData().hourlyTempData, result.dailyTempData[1].hourlyTempData)
    }

    private fun time(): List<String> {
        val time = mutableListOf<String>()
        time.add("2024-03-19T00:00")
        time.add("2024-03-19T01:00")
        time.add("2024-03-19T02:00")
        time.add("2024-03-20T10:00")
        time.add("2024-03-20T11:00")

        return time
    }

    private fun expectedFirstDayData(): DailyTempData {
        val expectedHourlyTempData = mutableMapOf<LocalTime, Double>()
        expectedHourlyTempData[LocalTime.of(0,0)] = 0.0
        expectedHourlyTempData[LocalTime.of(1,0)] = 1.0
        expectedHourlyTempData[LocalTime.of(2,0)] = 2.0

        return DailyTempData(LocalDate.of(2024, 3, 19), expectedHourlyTempData);
    }

    private fun expectedSecondDayData(): DailyTempData {
        val expectedHourlyTempData = mutableMapOf<LocalTime, Double>()
        expectedHourlyTempData[LocalTime.of(10,0)] = 3.0
        expectedHourlyTempData[LocalTime.of(11,0)] = 4.0

        return DailyTempData(LocalDate.of(2024, 3, 20), expectedHourlyTempData);
    }

    private fun temp(): List<Double> {
        return List(5) { it.toDouble() }
    }
}