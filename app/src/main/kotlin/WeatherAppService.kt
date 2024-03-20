package hu.vanio.kotlin.feladat.ms

import com.google.gson.Gson
import khttp.get
import org.springframework.http.HttpStatusCode
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@Service
class WeatherAppService {
    fun displayTemperatureDailyAverage(meteoData: MeteoResponseData?): List<String> {
        val dailyAverage = mutableListOf<String>()
        if (meteoData != null) {
            var dailySum = 0.0
            var actualDay = meteoData.hourly.time[0].substring(0, 10)
            var dailyIndex = 0
            meteoData.hourly.temperature_2m.forEachIndexed { index, temperature ->
                val dayToCheck = meteoData.hourly.time[index].substring(0, 10)
                if (dayToCheck != actualDay) {
                    if (dailySum != 0.0) {
                        val actualAverage = actualDay + " temperature: " + dailySum / dailyIndex
                        println(actualAverage)
                        dailyAverage.add(actualAverage)
                    }
                    dailySum = 0.0
                    dailyIndex = 0
                    actualDay = dayToCheck
                }
                dailyIndex++
                dailySum += temperature
            }

            //last day too
            if (dailySum != 0.0) {
                val actualAverage = actualDay + " temperature: " + dailySum / dailyIndex
                println(actualAverage)
                dailyAverage.add(actualAverage)
            }
            return dailyAverage
        } else {
            println("Failed to calculate the daily average temperature.")
            throw Exception("Failed to calculate the daily average temperature.")
        }
    }

    fun fetchMeteoData(apiUrl: String): MeteoResponseData? {
        val response = get(apiUrl)
        if (response.statusCode == 200) {
            return Gson().fromJson(response.text, MeteoResponseData::class.java)
        } else {
            throw HttpServerErrorException(HttpStatusCode.valueOf(response.statusCode))
        }

    }
}