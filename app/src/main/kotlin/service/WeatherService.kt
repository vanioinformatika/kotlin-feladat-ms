package hu.vanio.kotlin.feladat.ms.service

import hu.vanio.kotlin.feladat.ms.data.DailyAverageData
import hu.vanio.kotlin.feladat.ms.data.DailyTempDataContainer
import hu.vanio.kotlin.feladat.ms.httpclient.WeatherFeignClient
import hu.vanio.kotlin.feladat.ms.parser.WeatherResponseParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class WeatherService(
    private val weatherFeignClient: WeatherFeignClient,
    private val weatherResponseParser: WeatherResponseParser,
    private val tempCalculator: TempCalculator
) {
    @Throws(IllegalArgumentException::class)
    suspend fun getDailyTempData(): DailyTempDataContainer {
        val response = withContext(Dispatchers.IO) {
            weatherFeignClient.getWeather()
        }
        return weatherResponseParser.groupToDailyData(response.hourly)
    }

    @Throws(IllegalStateException::class)
    suspend fun getDailyAverageTemp(): List<DailyAverageData> {
        val dailyAverageTempList = mutableListOf<DailyAverageData>()
        val weeklyTempData = getDailyTempData()
        for (dailyTempData in weeklyTempData.dailyTempData) {
            val average = tempCalculator.getAverageDailyTemp(dailyTempData);
            dailyAverageTempList.add(DailyAverageData(dailyTempData.date, average))
        }
        return dailyAverageTempList
    }
}