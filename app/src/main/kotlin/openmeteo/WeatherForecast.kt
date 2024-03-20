package hu.vanio.kotlin.feladat.ms.openmeteo

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherForecast (
        val latitude: Double,
        val longitude: Double,
        @JsonProperty("generationtime_ms")
        val generationtimeMs: Double,
        @JsonProperty("utc_offset_seconds")
        val utcOffsetSeconds: Long,
        val timezone: String,
        @JsonProperty("timezone_abbreviation")
        val timezoneAbbreviation: String,
        val elevation: Double,
        @JsonProperty("hourly_units")
        val hourlyUnits: HourlyUnits,
        val hourly: Hourly,
)

data class HourlyUnits(
        val time: String,
        @JsonProperty("temperature_2m")
        val temperature2m: String,
)

data class Hourly(
        val time: List<String>,
        @JsonProperty("temperature_2m")
        val temperature2m: List<Double>,
)