package service

import hu.vanio.kotlin.feladat.ms.data.DailyTempData
import hu.vanio.kotlin.feladat.ms.service.TempCalculator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

class TempCalculatorTest {
    private val calculator = TempCalculator()

    @Test
    fun `test getAverage`() {
        val dailyTempData = dailyTempData()

        val expectedAverage = (10.0 + 15.0 + 20.0) / 3

        val result = calculator.getAverageDailyTemp(dailyTempData)

        assertEquals(expectedAverage, result)
    }

    @Test
    fun `test getAverage with empty data`() {
        val dailyTempData = DailyTempData(
            LocalDate.of(2024, 3, 19),
            hourlyTempData = emptyMap()
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            calculator.getAverageDailyTemp(dailyTempData)
        }

        assertEquals("Hourly temperature data for 2024-03-19 is empty.", exception.message)
    }

    private fun dailyTempData(): DailyTempData {
        val expectedHourlyTempData = mutableMapOf<LocalTime, Double>()
        expectedHourlyTempData[LocalTime.of(0,0)] = 10.0
        expectedHourlyTempData[LocalTime.of(1,0)] = 15.0
        expectedHourlyTempData[LocalTime.of(2,0)] = 20.0

        return DailyTempData(LocalDate.of(2024, 3, 19), expectedHourlyTempData);
    }
}