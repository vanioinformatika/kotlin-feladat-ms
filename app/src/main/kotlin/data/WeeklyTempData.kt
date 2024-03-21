package hu.vanio.kotlin.feladat.ms.data

import java.time.LocalDate

data class WeeklyTempData(var from: LocalDate?, var to: LocalDate?, val dailyTempData: List<DailyTempData>)