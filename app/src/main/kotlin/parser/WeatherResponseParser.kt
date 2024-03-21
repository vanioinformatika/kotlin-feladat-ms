package hu.vanio.kotlin.feladat.ms.parser

import hu.vanio.kotlin.feladat.ms.data.DailyTempData
import hu.vanio.kotlin.feladat.ms.data.HourlyData
import hu.vanio.kotlin.feladat.ms.data.WeeklyTempData
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class WeatherResponseParser {

    @Throws(IllegalArgumentException::class)
    fun groupToWeekData(hourlyData: HourlyData): WeeklyTempData {
        val dailyTempDataList = mutableListOf<DailyTempData>()
        val (time, temp) = validateData(hourlyData)

        val days = time.map { LocalDate.parse(it.substring(0, 10)) }.distinct()
        for (day in days) {
            val hourlyTempsForDay = time.zip(temp)
                .filter { it.first.toDate() == day }
                .map { it.first.toTime() to it.second }
                .toMap()

            dailyTempDataList.add(DailyTempData(day, hourlyTempsForDay))
        }

        return weeklyTempData(dailyTempDataList)
    }

    private fun validateData(hourlyData: HourlyData): Pair<List<String>, List<Double>> {
        val time = hourlyData.time
        val temp = hourlyData.temperature_2m

        if (time.isNullOrEmpty()) {
            throw IllegalArgumentException("No time data available")
        }

        if (temp.isNullOrEmpty()) {
            throw IllegalArgumentException("No temp data available")
        }

        return time to temp
    }

    private fun weeklyTempData(dailyTempDataList: MutableList<DailyTempData>): WeeklyTempData {
        val from = dailyTempDataList.first().date
        val to = dailyTempDataList.last().date
        return WeeklyTempData(from, to, dailyTempDataList)
    }

    private fun String.toDate(): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        return LocalDate.parse(this, formatter)
    }

    private fun String.toTime(): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime = LocalDateTime.parse(this, formatter)
        return dateTime.toLocalTime()
    }
}
