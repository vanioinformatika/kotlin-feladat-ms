package hu.vanio.kotlin.feladat.ms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.google.gson.Gson
import khttp.get
import org.springframework.http.HttpStatusCode

@SpringBootApplication
class WeatherApp

fun main() {
    runApplication<WeatherApp>()
    println("STARTUP")


}


