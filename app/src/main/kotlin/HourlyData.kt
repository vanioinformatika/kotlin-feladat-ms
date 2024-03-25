package hu.vanio.kotlin.feladat.ms

import java.time.LocalDateTime


data class HourlyData(val time: Array<LocalDateTime>, val temperature_2m: Array<String>)
