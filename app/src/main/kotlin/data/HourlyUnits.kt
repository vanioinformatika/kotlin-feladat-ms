package hu.vanio.kotlin.feladat.ms.data

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnits(
    @JsonProperty("time") val time: String,
    @JsonProperty("temperature_2m") val temperature_2m: String
) {
}