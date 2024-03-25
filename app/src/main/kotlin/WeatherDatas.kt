package hu.vanio.kotlin.feladat.ms

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

data class MeteoData(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val generationtime_ms: Double = 0.0,
        val utc_offset_seconds : Int = 0,
        val timezone: String = "",
        val timezone_abbreviation: String = "",
        val elevation : Double= 0.0,
        val hourly_units : HourlyUnits = HourlyUnits(),
        val hourly : Hourly = Hourly(),
)
data class HourlyUnits(
        val time: String = "",
        val temperature_2m: String = "",
)
data class Hourly(
        val time: List<String> = mutableListOf(),
        val temperature_2m: List<Double> = mutableListOf(),
)
fun getJsonDataFromUrl(url: String): String {
    val connection = URL(url).openConnection()
    val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
    val jsonData = StringBuilder()

    var line: String?
    while (reader.readLine().also { line = it } != null) {
        jsonData.append(line)
    }
    reader.close()

    return jsonData.toString()
}
fun getMeteoDataFromJson(jsonStr: String): MeteoData {
    try {
        return jacksonObjectMapper().readValue(jsonStr)
    }
    catch (e1 : com.fasterxml.jackson.core.JsonParseException)
    {
        println("Wrong formed JSON: " + e1.toString())
        return MeteoData()
    }
    catch (e2: com.fasterxml.jackson.databind.exc.MismatchedInputException)
    {
        println("Wrong formed JSON: " + e2.toString())
        return MeteoData()
    }
}
fun getMeteoDataFromUrl(url: String): MeteoData {
    try
    {
        val jsonData = getJsonDataFromUrl(url)
        //val jsonData = getIt()
        return jacksonObjectMapper().readValue(jsonData)
    }
    catch (e : java.net.UnknownHostException)
    {
        println("Wrong URL: " +  e.toString())
        return MeteoData()
    }
    catch (e2: Exception) {
        println("Serious error: " + e2.toString())
        return MeteoData()
    }
}
