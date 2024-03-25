package hu.vanio.kotlin.feladat.ms.data

import java.time.LocalDate
import java.time.LocalTime

data class DailyTempData(var date: LocalDate, val hourlyTempData: Map<LocalTime, Double>)
