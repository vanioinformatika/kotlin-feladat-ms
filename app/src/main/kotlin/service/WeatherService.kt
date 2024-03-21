package hu.vanio.kotlin.feladat.ms.service

import hu.vanio.kotlin.feladat.ms.data.DailyAverageData
import hu.vanio.kotlin.feladat.ms.data.WeeklyTempData
import hu.vanio.kotlin.feladat.ms.httpclient.WeatherFeignClient
import hu.vanio.kotlin.feladat.ms.parser.WeatherResponseParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class WeatherService(
    private val weatherFeignClient: WeatherFeignClient,
    private val weatherResponseParser: WeatherResponseParser,
    private val averageDailyTempCalculator: AverageDailyTempCalculator
) {
    @Throws(IllegalArgumentException::class)
    suspend fun getWeeklyTempData(): WeeklyTempData {
        val response = withContext(Dispatchers.IO) {
            weatherFeignClient.getWeather()
        }
        return weatherResponseParser.groupToWeekData(response.hourly)
    }

    @Throws(IllegalStateException::class)
    suspend fun getDailyAverageTempForOneWeek(): List<DailyAverageData> {
        val dailyAverageTempList = mutableListOf<DailyAverageData>()
        val weeklyTempData = getWeeklyTempData()
        for (dailyTempData in weeklyTempData.dailyTempData) {
            val average = averageDailyTempCalculator.getAverageTemp(dailyTempData);
            dailyAverageTempList.add(DailyAverageData(dailyTempData.date, average))
        }
        return dailyAverageTempList
    }
}