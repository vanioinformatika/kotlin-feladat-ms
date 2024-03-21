package hu.vanio.kotlin.feladat.ms.data

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
data class HourlyData(
    @JsonProperty("time")
    val time: List<String>?,
    @JsonProperty("temperature_2m")
    val temperature_2m: List<Double>?
)
