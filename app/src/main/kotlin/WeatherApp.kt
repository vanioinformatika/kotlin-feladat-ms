package hu.vanio.kotlin.feladat.ms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherApp

fun main() {
    runApplication<WeatherApp>()

    val latitudeCoord = 47.4984
    val longitudeCoord = 19.0404
    //val latitudeCoord = 47.58446085308398
    //val longitudeCoord = 18.877541667963346
    WeatherCalculate.printDayAverageTemperaturesAt( latitudeCoord,  longitudeCoord)
}

