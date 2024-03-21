package hu.vanio.kotlin.feladat.ms.service

import hu.vanio.kotlin.feladat.ms.data.DailyTempData
import org.springframework.stereotype.Service

@Service
class AverageDailyTempCalculator {
    fun getAverageTemp(dailyTempData: DailyTempData): Double {
        if (dailyTempData.hourlyTempData.isEmpty()){
            throw IllegalArgumentException("Hourly temperature data for ${dailyTempData.date} is empty.")
        }

        return getAverage(dailyTempData)
    }

    private fun getAverage(dailyTempData: DailyTempData): Double {
        val sum = dailyTempData.hourlyTempData.values.sum()
        val size = dailyTempData.hourlyTempData.size
        return sum / size
    }
}

