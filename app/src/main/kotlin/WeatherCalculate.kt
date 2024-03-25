package hu.vanio.kotlin.feladat.ms

typealias StringToDoubles = MutableMap<String, MutableList<Double> >
class WeatherCalculate()
{
    companion object {
        fun recordData(meteoData: MeteoData) : StringToDoubles {
            var dayTemperatures : StringToDoubles = mutableMapOf()
            for (i in 0 until meteoData.hourly.time.size) {
                val day = meteoData.hourly.time[i].substring(0 .. 9)
                dayTemperatures.getOrPut(day, ::mutableListOf).add(meteoData.hourly.temperature_2m[i])
            }
            return dayTemperatures
        }
        fun printDayAverages(dayTemperatures : StringToDoubles) {
            println("Day average temperatures:")
            dayTemperatures.forEach { entry ->
                println("${entry.key}: ${entry.value.average()}")
            }
        }
        fun getDayAverages(dayTemperatures : StringToDoubles) : MutableMap<String, Double> {
            var ret : MutableMap<String, Double> = mutableMapOf()
            dayTemperatures.forEach { entry ->
                ret.put(entry.key, entry.value.average())
            }
            return ret
        }
        fun printDayAverageTemperaturesAt(latitudeCoord : Double, longitudeCoord : Double) {
            val meteoUrl = "https://api.open-meteo.com/v1/forecast?latitude=$latitudeCoord&longitude=$longitudeCoord&hourly=temperature_2m&timezone=auto"
            val meteoData = getMeteoDataFromUrl(meteoUrl)
            if (meteoData.hourly.time.isNotEmpty()) {
                val dayTemps = WeatherCalculate.recordData(meteoData)
                WeatherCalculate.printDayAverages(dayTemps)
            }
        }
    }
}
