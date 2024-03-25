package hu.vanio.kotlin.feladat.ms

data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Int,
    val hourly_units: HourlyUnits,
    val hourly: HourlyData
    )
