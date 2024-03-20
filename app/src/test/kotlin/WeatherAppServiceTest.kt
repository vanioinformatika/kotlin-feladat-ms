package hu.vanio.kotlin.feladat.ms

import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.UnknownHostException

class WeatherAppServiceTest {

    var weatherAppService = WeatherAppService()
    @Test
    fun fetchDataTest() {
        val latitude = 47.4984
        val longitude = 19.0404
        val apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&hourly=temperature_2m&timezone=auto"

        val meteoData = weatherAppService.fetchMeteoData(apiUrl)

        assertTrue(meteoData != null)
        assertEquals(24*7, meteoData!!.hourly.time.size)
        assertEquals(24*7, meteoData!!.hourly.temperature_2m.size)
    }

    @Test
    fun fetchDataTestBadUrl() {
        val apiUrl = "https://badApi"
        assertThrows(UnknownHostException::class.java) {
            weatherAppService.fetchMeteoData(apiUrl)
        }
    }

    @Test
    fun displayTemperatureDailyAverageTest() {
        val formattedString = """{
   "latitude":47.5,
   "longitude":19.0625,
   "generationtime_ms":0.026941299438476562,
   "utc_offset_seconds":3600,
   "timezone":"Europe/Budapest",
   "timezone_abbreviation":"CET",
   "elevation":124.0,
   "hourly_units":{
      "time":"iso8601",
      "temperature_2m":"°C"
   },
   "hourly":{
      "time":[
         "2024-03-19T00:00",
         "2024-03-19T01:00",
         "2024-03-19T02:00",
         "2024-03-20T02:00",
         "2024-03-20T03:00",
         "2024-03-20T04:00"
      ],
      "temperature_2m":[
         3.4,
         3.2,
         2.9,
         2.7,
         2.5,
         2.6
      ]
    }
   }"""
        val meteoResponse = Gson().fromJson(formattedString, MeteoResponseData::class.java)
        val result = weatherAppService.displayTemperatureDailyAverage(meteoResponse)
        assertEquals("2024-03-19 temperature: 3.1666666666666665", result[0])
        assertEquals("2024-03-20 temperature: 2.6", result[1])
    }
}