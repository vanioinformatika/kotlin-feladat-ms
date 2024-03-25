package hu.vanio.kotlin.feladat.ms.service

import hu.vanio.kotlin.feladat.ms.data.DailyTempData
import org.springframework.stereotype.Service

@Service
class TempCalculator {
    fun getAverageDailyTemp(dailyTempData: DailyTempData): Double {
        if (dailyTempData.hourlyTempData.isEmpty()){
            throw IllegalArgumentException("Hourly temperature data for ${dailyTempData.date} is empty.")
        }

        return dailyTempData.hourlyTempData.values.average()
    }


}

