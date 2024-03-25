package hu.vanio.kotlin.feladat.ms.data

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @JsonProperty("latitude")
    val latitude: Double,
    @JsonProperty("longitude")
    val longitude: Double,
    @JsonProperty("generationtime_ms")
    val generationtime_ms: Double,
    @JsonProperty("utc_offset_seconds")
    val utc_offset_seconds: Int,
    @JsonProperty("timezone")
    val timezone: String,
    @JsonProperty("timezone_abbreviation")
    val timezone_abbreviation: String,
    @JsonProperty("elevation")
    val elevation: Double,
    @JsonProperty("hourly_units")
    val hourly_units: HourlyUnits,
    @JsonProperty("hourly")
    val hourly: HourlyData
)
