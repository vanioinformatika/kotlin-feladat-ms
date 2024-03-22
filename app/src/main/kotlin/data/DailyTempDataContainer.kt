package hu.vanio.kotlin.feladat.ms.data

import java.time.LocalDate

data class DailyTempDataContainer(var from: LocalDate?, var to: LocalDate?, val dailyTempData: List<DailyTempData>)